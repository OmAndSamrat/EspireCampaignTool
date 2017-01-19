package com.espire.campaign.contact.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.espire.campaign.contact.dao.ContactDao;
import com.espire.domain.Contact;

@Stateless
@TransactionManagement(value=TransactionManagementType.CONTAINER)
@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
public class ContactService {
	
	@PersistenceContext(unitName = "campaign-pu")
	EntityManager em;
	
	ContactDao contactDao;
	
	@PostConstruct
	public void init(){
		contactDao = new ContactDao(em); 
	}
	
	public List<Contact> getContactByOrganization(Long orgId){
		return contactDao.getContactByOrganization(orgId); 
	}
	
	public Contact createContactInOraganzation(Long organizationId, Contact contact){
		return contactDao.createContactInOraganzation(organizationId, contact);
	}
	
	public List<Contact> searchContacts(String firstName,String lastName,
			String email,Long designationId,Long orgId,Long designationGrpId,
			Integer resultIndex,Integer resultCount){
		return contactDao.searchContacts( firstName, lastName,
				 email, designationId, orgId, designationGrpId,
				 resultIndex, resultCount);
	}

}
