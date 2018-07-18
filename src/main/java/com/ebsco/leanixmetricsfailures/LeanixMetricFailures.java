package com.ebsco.leanixmetricsfailures;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.leanix.metrics.api.PointsApi;
import net.leanix.metrics.api.models.Field;
import net.leanix.metrics.api.models.Point;

public class LeanixMetricFailures 
{
	//below are the object paths for leanix api clients and client builders
	//the common clients/builders are used for accessing information about factsheets
	//the dropkit client/builders are used for posting that information to leanix metrics
    /*
	private net.leanix.api.common.ApiClient commonClient;
	private net.leanix.api.common.ApiClientBuilder commonClientBuidler;
	private net.leanix.dropkit.apiclient.ApiClient metricClient;
	private net.leanix.dropkit.apiclient.ApiClientBuilder metricClientBuilder;
    */
	private String apiToken = "";
	private String workspaceID = "";
	
	public LeanixMetricFailures(String at, String wi) {
		this.apiToken = at;
		this.workspaceID = wi;
	}
    
	//create a Metrics API object to post to leanix metrics
    public PointsApi LeanixMetricsAPI() {
    	net.leanix.dropkit.apiclient.ApiClient apiClient = new net.leanix.dropkit.apiclient.ApiClientBuilder()
    			//url for the post request
    			.withBasePath("https://us.leanix.net/services/metrics/v1")
    			//to get oauth2 token
    			.withTokenProviderHost("us.leanix.net")
    			//api token from the workspace you'll be using
    			.withApiToken(this.apiToken)
    			.build();
    	//create the api object and return it
    	PointsApi pointsApi = new PointsApi(apiClient);
    	return pointsApi;
    }
    
    //post the point given the incomplete factsheet information
    public void pushPoint(Map<String, Integer> metrics) {
    	//create an empty point
    	Point point = new Point();
    	
    	//General Information
    	//get the current time from UTC. When I tried making it EST, the post request failed
    	OffsetDateTime currentTime= OffsetDateTime.now(ZoneOffset.UTC);
    	//use OffsetDateTime.parse("2018-03-17T13:21:42.318Z") to use a specific date/time
    	point.setTime(currentTime);
    	System.out.println(currentTime);
    	
    	//Title of the metrics.
    	point.setMeasurement("Audit Report Metric Failures Tests");
    	//workspace id. Should be from same workspace as api token. It can be found in the API tokens tab
    	//in the Admin panel
    	point.setWorkspaceId(this.workspaceID);

    	//the field will hold the key value pair. The key is the failure type and the value is the
    	//number of failures
    	Field tempField = new Field();
    	for (String key : metrics.keySet()) {
    		tempField = new Field();
    		//set key to factsheet type
    		tempField.setK(key);
    		//set value to number of incomplete factsheets
    		tempField.setV(metrics.get(key).doubleValue());
    		//add the field to the list
    		point.addFieldsItem(tempField);
    	}
    	
    	//No tags are used now but the below code is how you would add it if you wanted to
    	/*
    	Tag t1 = new Tag();
    	t1.setK("factsheetID");
    	t1.setV("123456");
    	point.addTagsItem(t1);
    	*/
    	
    	System.out.println(point);
    	
    	//get the api object used to post the new point
       	PointsApi pa = LeanixMetricsAPI();
    	
       	//try to post the point, else there's an error
    	try {
			pa.createPoint(point);
			System.out.println("Point Created");
		} catch (net.leanix.dropkit.apiclient.ApiException e) {
			e.printStackTrace();
			System.out.println("Error posting point");
		}
		
    }
    
    //apiclient used to get information about factsheets
    public net.leanix.api.common.ApiClient QueryClient() {
    	net.leanix.api.common.ApiClient apiClient = new net.leanix.api.common.ApiClientBuilder()
    			//url to make get request from
    			.withBasePath("https://us.leanix.net/services/pathfinder/v1")
    			//api token from the workspace you will be using
    			.withApiToken(this.apiToken)
    			//leanix server
    			.withTokenProviderHost("us.leanix.net")
    			.build();
    	return apiClient;
    }
    
    //get the number of incomplete factsheets of the given type
    public FilterTools LoadFilterFactsheets() {
    	
    	//list of factsheet types, there should be a .graphql file for each type
    	String[] types = {"boundedContext", "domain", "dataObject", "ITComponent", "behavior",
    			"useCase", "epic", "persona"};
    	
    	FilterTools ft = new FilterTools();
    	
    	for (int i = 0; i < types.length; i++) {
    		//get the api client
        	net.leanix.api.common.ApiClient apiClient = QueryClient();
        	
        	//create a query object
        	Query query = new Query();
        	//send the api client and type of factsheet to query to get a map of factsheet information
    		Map<String, Map<String, Object>> data = query.getInfo(apiClient, "/" + types[i] + ".graphql");
    		//get the list of edges from the map
    		List<Map<String, Object>> edgeList = (List<Map<String, Object>>) data.get("allFactSheets").get("edges");
    		
    		ft.setCurrentList(edgeList);
    		ft.setType(types[i]);
    		ft.filterData();
    		
    	}

		return ft;

    }
    
    //Main function
    public static void main (String[] args) {
    	
    	String apiToken = "";
    	String workspaceID = "f8897aa0-9602-4217-8d78-714ac1ea7e7d";
    	
    	//create a new LeanixMetrics Object
    	LeanixMetricFailures lm = new LeanixMetricFailures(apiToken, workspaceID);
    	
    	//the process can take a few seconds so let the user know it's starting
    	System.out.println("Starting...");
    	
    	/* Failure Types
    	 * relation
    	 * Accountable
    	 * Responsible
    	 * Business Criticality
    	 * Owner Persona
    	 * Functional Fit
    	 * Technical Fit
    	 * Quality Seal
    	 * Model Status
    	 * Score
    	 * Documents
    	 * Lifecycle
    	 * Business Value Risk
    	 */
    	
    	FilterTools ft = lm.LoadFilterFactsheets();
    	
    	System.out.println("Info Loaded");
    	
    	Map<String, Integer> metrics = new HashMap<String, Integer>();
    	metrics.put("relation", ft.getRelationSize());
    	metrics.put("accountable", ft.getAccountableSize());
    	metrics.put("responsible", ft.getRepsonsibleSize());
    	metrics.put("businessCriticality", ft.getBusinessCriticalitySize());
    	metrics.put("ownerPersona", ft.getOwnerPersonaSize());
    	metrics.put("functionalFit", ft.getFunctionalFitSize());
    	metrics.put("technicalFit", ft.getTechnicalFitSize());
    	metrics.put("qualitySeal", ft.getQualitySealSize());
    	metrics.put("modelStatus", ft.getModelStatusSize());
    	metrics.put("score", ft.getScoreSize());
    	metrics.put("documents", ft.getDocumentsSize());
    	metrics.put("lifecycle", ft.getLifecycleSize());
    	metrics.put("businessValueRisk", ft.getBusinessValueRiskSize());
    	
    	System.out.println(metrics.toString());

    	
    	//push the number of failures
    	lm.pushPoint(metrics);
    }
    
}
