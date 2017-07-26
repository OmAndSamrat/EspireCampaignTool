package com.espire.campaign.htmlparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.espire.campaign.camp.dao.CampaignDao;
import com.espire.domain.EdmEvent;
import com.espire.domain.Event;

public class HtmlParser {
	private CampaignDao campaignDao;
	private Long edmId;
	
	public HtmlParser(CampaignDao campaignDao, Long edmId) {
		this.campaignDao = campaignDao;
		this.edmId = edmId;
	}
	
	Event createEvent(String eventName, String eventDescription,Long eventGroupID, String href) {
		Event event =  new Event();
		event.setEventName(eventName);
		event.setEventDescription(eventDescription);
		event.setEventGroupID(eventGroupID);
		event.setHref(href);
		return event;
	}
	EdmEvent createEdmEvent(Long edmId, Event event) {
		EdmEvent edmEvent = new EdmEvent();
		edmEvent.setEdmID(edmId);
		edmEvent.setEvent(event);
		return edmEvent;
	}
	
	public String parsedHtml(String html) {
		String dummyQuery = "?v=1&amp;tid=UA-27867819-1&amp;cid=lunch-event&amp;t=event&amp;ec=email&amp;ea=open&amp;el=lunch-event&amp;cs=emailer&amp;cm=emailer&amp;cn=lunch-event";
		Document document = Jsoup.parse(html, "UTF-8");
		//Email check event creation. 
		Event emailCheckEvent = createEvent("Email Check", "Email checked by User",1L,"transparentImage");
		campaignDao.createEvent(emailCheckEvent);
		EdmEvent edmEvent = createEdmEvent(this.edmId,emailCheckEvent);
		campaignDao.createEdmEvent(edmEvent);
		document.select("body").first().children().first().before("<img src='%baseUrl%/img/%communicationtrackerId%/"+edmEvent.getEventEDMID()+dummyQuery+"'></img>");
		
		//Click able Href event creation.
		Event hrefEvent;
		EdmEvent hrefEdmEvent;
		int i =0;
		Elements links = document.select("a[href]");
		for(Element link : links) {
			String hrefValue = link.attr("href");
			if(null!=hrefValue && !"".equals(hrefValue) && !"#".equals(hrefValue)) {
				i++;
				System.out.println(link.text());
				System.out.println(link.attr("href"));
				hrefEvent = createEvent("Link Click"+i, link.text(),2L,link.attr("href"));
				hrefEvent = campaignDao.createEvent(hrefEvent);
				hrefEdmEvent = createEdmEvent(this.edmId,hrefEvent);
				campaignDao.createEdmEvent(hrefEdmEvent);
				link.attr("href","%baseUrl%/href/%communicationtrackerId%/"+hrefEdmEvent.getEventEDMID());
				System.out.println(link.attr("href"));
			}
			
		}
		return document.toString();
	}
}

