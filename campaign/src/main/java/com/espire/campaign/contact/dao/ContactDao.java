package com.espire.campaign.contact.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
		
		if (organizationId==null || contact.getDesignation()==null || contact.getDesignation().getDesignationId()==null)
			return null;
		Organization org = em.find(Organization.class, organizationId);
		Designation designation = em.find(Designation.class, contact.getDesignation().getDesignationId());
		contact.setOrganization(org);
		contact.setDesignation(designation);
		em.persist(contact);
		return contact;
		
		
	}
	
}
