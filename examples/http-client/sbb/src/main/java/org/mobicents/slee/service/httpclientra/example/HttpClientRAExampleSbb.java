package org.mobicents.slee.service.httpclientra.example;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.slee.ActivityContextInterface;
import javax.slee.CreateException;
import javax.slee.RolledBackContext;
import javax.slee.SbbContext;
import javax.slee.facilities.TimerEvent;
import javax.slee.facilities.TimerFacility;
import javax.slee.facilities.TimerOptions;
import javax.slee.facilities.TimerPreserveMissed;
import javax.slee.facilities.Tracer;

import net.java.client.slee.resource.http.HttpClientActivity;
import net.java.client.slee.resource.http.HttpClientActivityContextInterfaceFactory;
import net.java.client.slee.resource.http.HttpClientResourceAdaptorSbbInterface;
import net.java.client.slee.resource.http.event.ResponseEvent;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * This is a simple Service to demonstrate the usage of Http-client-ra This
 * service reads the RSS Feed link from sbb-jar.xml and fetches the links
 * periodically using ROME api (Look at https://rome.dev.java.net/ to know more
 * about ROME.)
 * 
 * If there are changes in the RSS Feed, this service retrieves the content of
 * the link
 * 
 * @author amit.bhayani
 * 
 */
public abstract class HttpClientRAExampleSbb implements javax.slee.Sbb {

	private Tracer tracer;

	private SbbContext sbbContext; // This SBB's SbbContext

	private TimerFacility timerFacility;

	private HttpClientActivityContextInterfaceFactory httpClientAci;

	private HttpClientResourceAdaptorSbbInterface raSbbInterface;

	private long refreshTime;

	private String rssFeedString;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.slee.Sbb#setSbbContext(javax.slee.SbbContext)
	 */
	public void setSbbContext(SbbContext context) {

		this.sbbContext = context;
		this.tracer = sbbContext.getTracer(HttpClientRAExampleSbb.class
				.getSimpleName());

		try {
			Context ctx = (Context) new InitialContext()
					.lookup("java:comp/env");

			httpClientAci = (HttpClientActivityContextInterfaceFactory) ctx
					.lookup("slee/resources/http-client/acifactory");

			raSbbInterface = (HttpClientResourceAdaptorSbbInterface) ctx
					.lookup("slee/resources/http-client/sbbinterface");

			// Getting Timer Facility interface
			timerFacility = (TimerFacility) ctx.lookup("slee/facilities/timer");

			refreshTime = ((Long) ctx.lookup("RefreshTime")).longValue();
			rssFeedString = (String) ctx.lookup("RSSFeedURL");

		} catch (NamingException ne) {
			tracer.severe("Could not set SBB context:", ne);
		}

	}

	// CMP Fields

	public abstract void setFeedHashCode(int feedHashCode);

	public abstract int getFeedHashCode();

	// Event handler methods

	public void onStartServiceEvent(
			javax.slee.serviceactivity.ServiceStartedEvent event,
			ActivityContextInterface aci) {

		tracer.info("<><><><><> \n onStartServiceEvent of HttpClientRAExampleSbb \n <><><><><><><>");

		processRssFeed();

		setTimer(aci);

	}

	private void processRssFeed() {
		try {
			URL rssFeedUrl = new URL(rssFeedString);

			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(rssFeedUrl));

			if (feed.hashCode() != this.getFeedHashCode()) {
				tracer.info("There is a new entry in the RSS Feed "
						+ rssFeedString);
				this.setFeedHashCode(feed.hashCode());

				List<?> list = feed.getEntries();
				Iterator<?> itr = list.iterator();
				while (itr.hasNext()) {
					SyndEntryImpl syndFeed = (SyndEntryImpl) itr.next();
					tracer.info("Getting the Content for URL "
							+ syndFeed.getLink());

					HttpGet httpGet = new HttpGet(syndFeed.getLink());

					try {

						HttpClientActivity clientActivity = raSbbInterface
								.createHttpClientActivity(true, null);

						ActivityContextInterface clientAci = httpClientAci
								.getActivityContextInterface(clientActivity);
						clientAci.attach(sbbContext.getSbbLocalObject());

						clientActivity.execute(httpGet, syndFeed.getLink());

					} catch (Throwable e) {
						tracer.severe(
								"Error while creating HttpClientActivity", e);
					}
				}

			} else {
				tracer.info("No new entry in the RSS Feed " + rssFeedString);
			}

		} catch (Exception e) {
			tracer.severe("Failed to process RSS Feed", e);
		}
	}

	private void setTimer(ActivityContextInterface aci) {

		TimerOptions options = new TimerOptions();
		options.setPreserveMissed(TimerPreserveMissed.ALL);

		this.timerFacility.setTimer(aci, null, System.currentTimeMillis()
				+ refreshTime, options);

	}

	public void onTimerEvent(TimerEvent event, ActivityContextInterface aci) {
		tracer.info("########## HttpClientRAExampleSbb: onTimerEvent ##########");

		processRssFeed();

		setTimer(aci);

	}

	public void onResponseEvent(ResponseEvent event,
			ActivityContextInterface aci) {
		HttpResponse response = event.getHttpResponse();
		tracer.info("********** onResponseEvent **************");
		tracer.info("URI = " + event.getRequestApplicationData());
		tracer.info("Status Code = " + response.getStatusLine().getStatusCode());
		try {
			tracer.info("Response Body = "
					+ EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			tracer.severe("Failed reading response body", e);
		}
		tracer.info("*****************************************");
	}

	// Unused SBB lifecycle methods

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.slee.Sbb#sbbActivate()
	 */
	public void sbbActivate() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.slee.Sbb#sbbCreate()
	 */
	public void sbbCreate() throws CreateException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.slee.Sbb#sbbExceptionThrown(java.lang.Exception,
	 * java.lang.Object, javax.slee.ActivityContextInterface)
	 */
	public void sbbExceptionThrown(Exception arg0, Object arg1,
			ActivityContextInterface arg2) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.slee.Sbb#sbbLoad()
	 */
	public void sbbLoad() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.slee.Sbb#sbbPassivate()
	 */
	public void sbbPassivate() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.slee.Sbb#sbbPostCreate()
	 */
	public void sbbPostCreate() throws CreateException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.slee.Sbb#sbbRemove()
	 */
	public void sbbRemove() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.slee.Sbb#sbbRolledBack(javax.slee.RolledBackContext)
	 */
	public void sbbRolledBack(RolledBackContext arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.slee.Sbb#sbbStore()
	 */
	public void sbbStore() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.slee.Sbb#unsetSbbContext()
	 */
	public void unsetSbbContext() {
	}

}
