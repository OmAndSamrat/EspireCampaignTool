package com.espire.campaign.microsite;

import javax.servlet.http.HttpServletRequest;

import com.espire.domain.CaptureInteraction;

public class MicroSiteUtil {
	private static final String[] HEADERS_TO_TRY = { 
		    "X-Forwarded-For",
		    "Proxy-Client-IP",
		    "WL-Proxy-Client-IP",
		    "HTTP_X_FORWARDED_FOR",
		    "HTTP_X_FORWARDED",
		    "HTTP_X_CLUSTER_CLIENT_IP",
		    "HTTP_CLIENT_IP",
		    "HTTP_FORWARDED_FOR",
		    "HTTP_FORWARDED",
		    "HTTP_VIA",
		    "REMOTE_ADDR" };

	public static String getIpAddress(HttpServletRequest request) {
			String ip = null;
		    for (String header : HEADERS_TO_TRY) {
		        ip = request.getHeader(header);
		        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
		            return ip;
		        }
		    }
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getRemoteAddr();  
		    } 
		    return request.getRemoteAddr();
	}
	
	public static CaptureInteraction createCommunicationTracker(HttpServletRequest request, Long communicationTrackerId, Long eventEdmId) {
		CaptureInteraction captureInteraction = new CaptureInteraction();
		String ipAddress = MicroSiteUtil.getIpAddress(request);
		captureInteraction.setCommunicationTrackerID(communicationTrackerId);
		captureInteraction.setEventEDMID(eventEdmId);
		captureInteraction.setiPAddress(ipAddress);
		UserAgentInfo userAgentInfo = new UserAgentInfo(request.getHeader("User-Agent"), request.getHeader("http-accept"));
		captureInteraction.setDevice(userAgentInfo.getUserAgent()); //Call User Agent Here. 
		Geo geo = IptoLangConvertor.getLatLong(ipAddress);
		captureInteraction.setLatitude(geo.getLatitude());
		captureInteraction.setLongitude(geo.getLongitude());
		return captureInteraction;
	}
}
