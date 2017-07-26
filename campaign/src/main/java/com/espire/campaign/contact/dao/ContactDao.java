package com.espire.campaign.contact.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.espire.domain.Communication;
import com.espire.domain.Contact;
import com.espire.domain.Designation;
import com.espire.domain.Organization;

public class ContactDao {

	EntityManager em;
	
	public ContactDao(EntityManager em){
		this.em = em;
	}
	
	public List<Contact> getContactByOrganization(Long orgId){
		TypedQuery<Contact> contactQuery = em.createQuery("select cont from Contact cont where cont.organization.organisationID=:orgId  "
					+"and cont.softDelete = 1",Contact.class);
		contactQuery.setParameter("orgId", orgId);
		
		List<Contact> contactList =  contactQuery.getResultList();
		
		return contactList;
		
	}
	
	public Contact createContactInOraganzation(Long organizationId, Contact contact){
		
		if (organizationId==null )
			return null;
		Organization org = em.find(Organization.class, organizationId);
		contact.setOrganization(org);
		if(contact.getDesignation()==null || contact.getDesignation().getDesignationId()==null){
			Designation designation = em.find(Designation.class, contact.getDesignation().getDesignationId());
			contact.setDesignation(designation);
		}
		em.persist(contact);
		return contact;
	}
	
	public List<Contact> searchContacts(String firstName,String lastName,
			String email,Long designationId,Long orgId,Long designationGrpId,String gender,
			Integer index,Integer count){
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Contact> cq = cb.createQuery(Contact.class);
		Root<Contact> contact = cq.from(Contact.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (firstName != null) {
	        predicates.add(
	                cb.equal(contact.get("firstName"), firstName));
	    }
		if (lastName != null) {
	        predicates.add(
	                cb.equal(contact.get("surName"), lastName));
	    }
		if (email != null) {
	        predicates.add(
	                cb.equal(contact.get("email"), email));
	    }
		if (designationId != null) {
	        predicates.add(
	                cb.equal(contact.get("designation").get("designationId"), designationId));
	    }
		if (designationGrpId != null) {
			predicates.add(
					cb.equal(contact.get("designation").get("designationGroup").get("designationGroupId"), designationGrpId));
		}
		if (gender != null) {
			predicates.add(
					cb.equal(contact.get("gender"), gender));
		}
		if (orgId != null) {
	        predicates.add(
	                cb.equal(contact.get("organization").get("organisationID"), orgId));
	    }
		 predicates.add(
	                cb.equal(contact.get("softDelete"), 1));
		cq.select(contact)
        .where(predicates.toArray(new Predicate[]{}));
		TypedQuery<Contact> contactQuery = em.createQuery(cq);
		if(index!=null && count!=null){
			contactQuery.setFirstResult(index);
			contactQuery.setMaxResults(count);
		}
		return contactQuery.getResultList();
	}
	public List<Contact> searchCampaignNonAssignedContacts(String firstName,String lastName,
			String email,Long designationId,Long orgId,Long designationGrpId,String gender,
			Integer index,Integer count, Long campaignId){
		List<Contact> nonAssignedContacts = null;
		List<Contact> contacts = this.searchContacts(firstName, lastName, email, designationId, orgId, designationGrpId, gender, index, count);
		TypedQuery<Communication> communicationQuery = em.createQuery("select com from Communication com "
				+"where com.campaign.campaignID = :campaignId and com.softDelete=1",Communication.class);
			communicationQuery.setParameter("campaignId", campaignId);
			List<Communication> communications = communicationQuery.getResultList();
			if(communications!=null && communications.size()>0) {
				List<Long> assignedContacts = communications.stream().map((communication)->communication.getContact().getContactID()).collect(Collectors.toList());
				nonAssignedContacts = contacts.stream().filter((contact) ->  !(assignedContacts.contains(contact.getContactID())) ).collect(Collectors.toList());
			} else {
				nonAssignedContacts = contacts;
			}
		return nonAssignedContacts;
		
	}
	
	
}
