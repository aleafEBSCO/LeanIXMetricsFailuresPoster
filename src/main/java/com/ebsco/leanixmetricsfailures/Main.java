package com.ebsco.leanixmetricsfailures;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class Main {
	//Main function
    public static void main (String[] args) {

    	if (args.length != 3) {
    		System.out.println("Not the right number of Arguments. Program Exiting");
    		System.exit(1);
		}

    	String apiToken = args[0];
    	String workspaceID = args[1];
    	String measurementsName = args[2];
    	
    	//create a new LeanixMetrics Object
    	LeanixMetricFailures lm = new LeanixMetricFailures(apiToken, workspaceID, measurementsName);

		//get the current time from UTC
		OffsetDateTime currentTime = OffsetDateTime.now(ZoneOffset.UTC);

		//if a point already has been posted today or it's after 7pm, the user can't post
		if (lm.pointAlreadyExists(currentTime)) {
			System.out.println("Exiting Program");
			System.exit(0);
		}

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
    	
    	if (ft == null) {
    		return;
    	}
    	
    	System.out.println("Info Loaded");
    	
    	Map<String, Integer> metrics = new HashMap<String, Integer>();
    	metrics.put("relation", ft.getRelationSize());
    	metrics.put("accountable", ft.getAccountableSize());
    	metrics.put("responsible", ft.getResponsibleSize());
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
