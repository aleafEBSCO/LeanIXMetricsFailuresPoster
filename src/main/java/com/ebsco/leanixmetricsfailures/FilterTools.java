package com.ebsco.leanixmetricsfailures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

//class to return the number of incomplete factsheets given all the factsheets of a certain type
public class FilterTools {
	
	//var to hold initial list of factsheet leafNodes
	private List<Map<String, Object>> currentList;
	//var to hold the incomplete factsheets. It's a set so there can't be duplicates
	
	//initiallize sets to hold factsheets that fail certain tests
	private Set<String> relation;
	private Set<String> accountable;
	private Set<String> responsible;
	private Set<String> businessCriticality;
	private Set<String> ownerPersona;
	private Set<String> functionalFit;
	private Set<String> technicalFit;
	private Set<String> qualitySeal;
	private Set<String> modelStatus;
	private Set<String> score;
	private Set<String> documents;
	private Set<String> lifecycle;
	private Set<String> businessValueRisk;
	
	private String type;
	//map to hold help convert ebsco type names to leanix type names
	private Map<String, String> ebscoToLeanix;
	
	//default constructor
	FilterTools() {
		//save the leafnodes from the list of nodes
		this.currentList = new ArrayList<Map<String, Object>>();
		//save the type
		this.type = "";
		
		//empty sets for test failures
		this.relation = new HashSet<String>();
		this.accountable = new HashSet<String>();
		this.responsible = new HashSet<String>();
		this.businessCriticality = new HashSet<String>();
		this.ownerPersona = new HashSet<String>();
		this.functionalFit = new HashSet<String>();
		this.technicalFit = new HashSet<String>();
		this.qualitySeal = new HashSet<String>();
		this.modelStatus = new HashSet<String>();
		this.score = new HashSet<String>();
		this.documents = new HashSet<String>();
		this.lifecycle = new HashSet<String>();
		this.businessValueRisk = new HashSet<String>();
		
		//ebsco type names to leanix type names
		this.ebscoToLeanix = new HashMap<String, String>();
		this.ebscoToLeanix.put("Domain", "BusinessCapability");
		this.ebscoToLeanix.put("Use Case", "Process");
		this.ebscoToLeanix.put("Persona", "UserGroup");
		this.ebscoToLeanix.put("Epic", "Project"); 
		this.ebscoToLeanix.put("Bounded Context", "Application");
		this.ebscoToLeanix.put("Behavior", "Interface");
		this.ebscoToLeanix.put("Data Object", "DataObject");
		this.ebscoToLeanix.put("IT Component", "ITComponent");
		this.ebscoToLeanix.put("Provider", "Provider");
		this.ebscoToLeanix.put("Technical Stack", "TechnicalStack");
		this.ebscoToLeanix.put("Provider Application", "ProviderApplication");
	}
	
	//constructor
	FilterTools(List<Map<String, Object>> info, String t) {
		//save the leafnodes from the list of nodes
		this.currentList = keepLeafNodes(info);
		//save the type
		this.type = t;
		
		//empty sets for test failures
		this.relation = new HashSet<String>();
		this.accountable = new HashSet<String>();
		this.responsible = new HashSet<String>();
		this.businessCriticality = new HashSet<String>();
		this.ownerPersona = new HashSet<String>();
		this.functionalFit = new HashSet<String>();
		this.technicalFit = new HashSet<String>();
		this.qualitySeal = new HashSet<String>();
		this.modelStatus = new HashSet<String>();
		this.score = new HashSet<String>();
		this.documents = new HashSet<String>();
		this.lifecycle = new HashSet<String>();
		this.businessValueRisk = new HashSet<String>();
		
		//ebsco type names to leanix type names
		this.ebscoToLeanix = new HashMap<String, String>();
		this.ebscoToLeanix.put("Domain", "BusinessCapability");
		this.ebscoToLeanix.put("Use Case", "Process");
		this.ebscoToLeanix.put("Persona", "UserGroup");
		this.ebscoToLeanix.put("Epic", "Project"); 
		this.ebscoToLeanix.put("Bounded Context", "Application");
		this.ebscoToLeanix.put("Behavior", "Interface");
		this.ebscoToLeanix.put("Data Object", "DataObject");
		this.ebscoToLeanix.put("IT Component", "ITComponent");
		this.ebscoToLeanix.put("Provider", "Provider");
		this.ebscoToLeanix.put("Technical Stack", "TechnicalStack");
		this.ebscoToLeanix.put("Provider Application", "ProviderApplication");
	}
	
	//set current data
	void setCurrentList(List<Map<String, Object>> cd) {
		this.currentList = keepLeafNodes(cd);
	}
	
	//set current type
	public void setType(String t) {
		this.type = t;
	}
	
	//get size methods for sets
	//initiallize sets to hold factsheets that fail certain tests
	
	int getRelationSize() {
		return this.relation.size();
	}
	
	int getAccountableSize() {
		return this.accountable.size();
	}

	int getResponsibleSize() {
		return this.responsible.size();
	}

	int getBusinessCriticalitySize() {
		return this.businessCriticality.size();
	}

	int getOwnerPersonaSize() {
		return this.ownerPersona.size();
	}
	
	int getFunctionalFitSize() {
		return this.functionalFit.size();
	}

	int getTechnicalFitSize() {
		return this.technicalFit.size();
	}

	int getQualitySealSize() {
		return this.qualitySeal.size();
	}

	int getModelStatusSize() {
		return this.modelStatus.size();
	}

	int getScoreSize() {
		return this.score.size();
	}
	
	int getDocumentsSize() {
		return this.documents.size();
	}
	
	int getLifecycleSize() {
		return this.lifecycle.size();
	}
	
	int getBusinessValueRiskSize() {
		return this.businessValueRisk.size();
	}
	
	//get size of current list size
	int getCurrentSize() {
		return this.currentList.size();
	}
	
	//method to figure out which type of filters should be applied to the given type
	void filterData() {
		//let the user know filtering has begun
		System.out.println(type + " filtering");
		//Bounded Context/Application filtering
		if (this.type.equals("boundedContext")) {
			filterLifecycle();
			filterBusinessCriticality();
			filterFunctionalFit();
			filterRelation("Domain");
			filterRelation("Use Case");
			filterOwnerPersona();
			filterRelation("Data Object");
			filterProvidedBehaviorRelation();
			filterTechnicalFit();
			filterSoftwareITRelation();
			filterDocuments();
			filterAccount();
			filterResponse();
			filterQualitySeal();
			filterModelStatus();
			filterScore(.7);
		}
		//domain/BusinessCapability filtering
		else if (this.type.equals("domain")) {
			filterRelation("Bounded Context");
			filterRelation("Use Case");
			filterAccount();
			filterResponse();
			filterQualitySeal();
			filterModelStatus();
			filterScore(.6);
		}
		//Data Object filtering
		else if (this.type.equals("dataObject")) {
			filterBoundedContextAndBehavior();
			filterAccount();
			filterResponse();
			filterQualitySeal();
			filterModelStatus();
			filterScore(.5);
		}
		//IT Component filtering
		else if (this.type.equals("ITComponent")) {
			filterRelation("Provider");
			filterDocuments();
			filterLifecycle();
			filterTechnicalFit();
			filterRelation("Behavior");
			filterEisProviderOwnerPersona();
			filterOwnerPersona();
			filterAccount();
			filterResponse();
			filterQualitySeal();
			filterModelStatus();
			filterScore(.7);
		}
		//Behavior/Interface filtering
		else if (this.type.equals("behavior")) {
			filterRelation("Provider Application");
			filterRelation("IT Component");
			filterAccount();
			filterResponse();
			filterQualitySeal();
			filterModelStatus();
			filterScore(.6);
		}
		//Use Case/Process filtering
		else if (this.type.equals("useCase")) {
			filterRelation("Domain");
			filterDocuments();
			filterLifecycle();
			filterRelation("Bounded Context");
			filterAccount();
			filterResponse();
			filterQualitySeal();
			filterModelStatus();
			filterScore(.60);
		}
		//Epic/Project filtering
		else if (this.type.equals("epic")) {
			filterDocuments();
			filterLifecycle();
			filterBusinessValueRisk();
			filterRelation("Domain");
			filterRelation("Use Case");
			filterAccount();
			filterResponse();
			filterQualitySeal();
			filterModelStatus();
			filterScore(.5);
		}
		//Persona/userGroup filtering
		else if (this.type.equals("persona")) {
			filterAccount();
			filterResponse();
			filterQualitySeal();
			filterModelStatus();
			filterScore(.5);

		}
		
	}
	
	//keeps factsheets that don't have children
	private List<Map<String, Object>> keepLeafNodes(List<Map<String, Object>> info) {
		//Iterator to go through factsheets (fs)
		Iterator<Map<String, Object>> it = info.iterator();
		while (it.hasNext()) {
			//get the node from each edge
			Map<String, Object> edge = it.next();
			Map<String, Object> node = (Map<String, Object>) edge.get("node");
			
			//get the nodes relToChild object
			Map<String, Integer> innerNode = (Map<String, Integer>) node.get("relToChild");
			//if the node has a child
			if (innerNode.get("totalCount") != 0) {
				//remove it from the list of nodes
				it.remove();
			}
		}
		//return leafNode factsheets
		return info;
	}
	
	//count fs that don't have the given relation
	void filterRelation(String relation) {
		//var to key to find relation
		String searchKey;
		//iterate through fs
		for (Map<String, Object> edge : this.currentList) {
			//get the node in each edge
			Map<String, Object> node = (Map<String, Object>) edge.get("node");
			//create the search key using the current type and given relation
			searchKey = "rel" + node.get("type").toString() + "To" + this.ebscoToLeanix.get(relation);
			//get the object returned from using the search key
			Map<String, Integer> innerNode = (Map<String, Integer>) node.get(searchKey);
			//if there is no connection
			if (innerNode.get("totalCount") == 0) {
				//add it to the set
				this.relation.add(node.get("id").toString());
			}
		}
	}
	
	//counts fs that don't have accountable
	void filterAccount() {
		//iterate through fs
		for (Map<String, Object> aCurrentList : this.currentList) {
			//bools to see if there's someone responsible and accountable
			boolean accountFound = false;

			//get the node from each edge
			Map<String, Object> node = (Map<String, Object>) aCurrentList.get("node");

			//get the subscriptions for the fs
			Map<String, Object> subscriptions = (Map<String, Object>) node.get("subscriptions");

			//go through subscriptions
			List<Map<String, Object>> subEdges = (List<Map<String, Object>>) subscriptions.get("edges");
			for (Map<String, Object> innerEdge : subEdges) {
				//get the node for each subscription
				Map<String, Object> innerNode = (Map<String, Object>) innerEdge.get("node");

				//see if the subsrictption is Accountable and save the info as a bool

				if (innerNode.get("type").toString().equals("ACCOUNTABLE")) {
					accountFound = true;
				}
			}

			//if there isn't both someone responsible and accountable
			if (!(accountFound)) {
				//add it to the set
				this.accountable.add(node.get("id").toString());
			}
		}
	}
	
	//counts fs that don't have responsible
	void filterResponse() {
		//iterate through fs
		for (Map<String, Object> aCurrentList : this.currentList) {
			//bools to see if there's someone responsible and accountable
			boolean responseFound = false;

			//get the node from each edge
			Map<String, Object> node = (Map<String, Object>) aCurrentList.get("node");

			//get the subscriptions for the fs
			Map<String, Object> subscriptions = (Map<String, Object>) node.get("subscriptions");

			//go through subscriptions
			List<Map<String, Object>> subEdges = (List<Map<String, Object>>) subscriptions.get("edges");
			for (Map<String, Object> innerEdge : subEdges) {
				//get the node for each subscription
				Map<String, Object> innerNode = (Map<String, Object>) innerEdge.get("node");

				//see if the subsrciption is Responsible, save the info as a bool
				if (innerNode.get("type").toString().equals("RESPONSIBLE")) {
					responseFound = true;
				}
			}

			//if there isn't both someone responsible and accountable
			if (!(responseFound)) {
				//add it to the set
				this.responsible.add(node.get("id").toString());
			}
		}
	}

	//counts fs that aren't connected to a bounded context or behavior
	void filterBoundedContextAndBehavior() {
		//iterate through edges
		for (Map<String, Object> edge : this.currentList) {
			//get node from each edge
			Map<String, Object> node = (Map<String, Object>) edge.get("node");
			//get keys to use to get connection information
			String contextKey = "rel" + node.get("type").toString() + "ToApplication";
			String behaviorKey = "rel" + node.get("type").toString() + "ToInterface";
			//get the relation info using the keys
			Map<String, Integer> contextRel = (Map<String, Integer>) node.get(contextKey);
			Map<String, Integer> behaviorRel = (Map<String, Integer>) node.get(behaviorKey);
			//if the fs has no connection to either
			if ((contextRel.get("totalCount") == 0) && (behaviorRel.get("totalCount") == 0)) {
				//add it to the set
				this.relation.add(node.get("id").toString());
			}
		}
	}
	
	//count fs with no Business Criticality or no description
	void filterBusinessCriticality() {
		//iterate through edges
		for (Map<String, Object> edge : this.currentList) {
			//get node from each edge
			Map<String, Object> node = (Map<String, Object>) edge.get("node");
			//if business criticalitty is null or description is null/blank
			if ((node.get("businessCriticality") == null) || (node.get("businessCriticalityDescription") == null)
							|| (node.get("businessCriticalityDescription").toString().equals(""))) {
				//add fs to set
				this.businessCriticality.add(node.get("id").toString());
			}
		}
	}

	//counts fs with no owner persona when EIS is the provider
	void filterEisProviderOwnerPersona() {
		//iterate through the edges
		for (Map<String, Object> edge : this.currentList) {
			//first check to see if eis is an owner
			//get the node from the edge
			Map<String, Object> node = (Map<String, Object>) edge.get("node");

			//bool to see hold if eis is provider
			boolean eisProvider = false;
			//key to find providers
			String providerKey = "rel" + node.get("type").toString() + "ToProvider";
			//get the information on the providers
			Map<String, Object> relation = (Map<String, Object>) node.get(providerKey);
			//get the list of provider edges
			ArrayList<Map<String, Object>> relEdgeList = (ArrayList<Map<String, Object>>) relation.get("edges");
			//go through provider edges
			for (Map<String, Object> innerEdge : relEdgeList) {
				//get the node from each edges
				Map<String, Object> innerNode = (Map<String, Object>) innerEdge.get("node");
				//get the provider factsheet
				Map<String, String> factsheet = (Map<String, String>) innerNode.get("factSheet");
				//if the display name of that factsheet is EID
				if (factsheet.get("displayName").equals("EIS")) {
					//then EIS is a provider
					eisProvider = true;
					break;
				}
			}

			//bool so hold if an owner is found
			boolean ownerFound = false;
			//if eis is a provider
			if (eisProvider) {
				//key to search user group relations
				String userGroupKey = "rel" + node.get("type").toString() + "ToUserGroup";
				//get the object of user group relations
				Map<String, Object> relation2 = (Map<String, Object>) node.get(userGroupKey);
				//get the list of user group edges
				ArrayList<Map<String, Object>> relEdgeList2 = (ArrayList<Map<String, Object>>) relation2.get("edges");
				//go through the edges
				for (Map<String, Object> innerEdge2 : relEdgeList2) {
					//get the node from each edge
					Map<String, String> innerNode2 = (Map<String, String>) innerEdge2.get("node");
					//if the node has a usage type and it's value is owner
					if ((innerNode2.get("usageType") != null)
									&& (innerNode2.get("usageType").equals("owner"))) {
						//then there is an owner
						ownerFound = true;
					}
				}
			}

			//if eis is the provider and there's no owner
			if ((eisProvider && !(ownerFound))) {
				//add to set
				this.ownerPersona.add(node.get("id").toString());
			}
		}
	}
	
	//count fs that don't have a functional fit or description
	void filterFunctionalFit() {
		//iterate through edges
		for (Map<String, Object> edge : this.currentList) {
			//get node from each edge
			Map<String, Object> node = (Map<String, Object>) edge.get("node");
			//if there's no functionSuitability or description doesn't exist/is empty 
			if ((node.get("functionalSuitability") == null) || (node.get("functionalSuitabilityDescription") == null)
							|| (node.get("functionalSuitabilityDescription").toString().equals(""))) {
				//add fs to set
				this.functionalFit.add(node.get("id").toString());
			}
		}
	}
	
	//count fs that don't have a techincal fit or description
	void filterTechnicalFit() {
		//iterate through edges
		for (Map<String, Object> edge : this.currentList) {
			//get node from each edge
			Map<String, Object> node = (Map<String, Object>) edge.get("node");
			//if there's no technical suitability or no/blank description 
			if ((node.get("technicalSuitability") == null) || (node.get("technicalSuitabilityDescription") == null)
							|| (node.get("technicalSuitabilityDescription").toString().equals(""))) {
				//add fs to set
				this.technicalFit.add(node.get("id").toString());
			}
		}
	}

	//count fs that have a broken quality seal
	void filterQualitySeal() {
		//iterate through edges
		for (Map<String, Object> edge : this.currentList) {
			//get node from edge
			Map<String, Object> node = (Map<String, Object>) edge.get("node");
			//if quality seal is broken
			if (node.get("qualitySeal").toString().equals("BROKEN")) {
				//add fs to set
				this.qualitySeal.add(node.get("id").toString());
			}
		}
	}
	
	//count fs that don't have a model status or have one that isn't ready
	void filterModelStatus() {
		//iterate through edges
		for (Map<String, Object> edge : this.currentList) {
			//get node from each edge
			Map<String, Object> node = (Map<String, Object>) edge.get("node");
			//get list of tags from node
			ArrayList<Map<String, Object>> tags = (ArrayList<Map<String, Object>>) node.get("tags");
			//bool to hold if the tag is found
			boolean tagFound = false;
			//go through tags
			for (Map<String, Object> currentTag : tags) {
				//get the current tag
				//get the group that the tag belongs tp
				Map<String, Object> currentTagGroup = (Map<String, Object>) currentTag.get("tagGroup");
				//if the tag group is state of model completeness
				if (currentTagGroup.get("name").toString().equals("State of Model Completeness")) {
					//the tag has been found
					tagFound = true;
					//if the tag name isn't ready
					if (!(currentTag.get("name").toString().equals("ready"))) {
						//add the fs to the set
						this.modelStatus.add(node.get("id").toString());
						break;
					}
				}
			}
			//if there is no state of model completeness status
			if (!(tagFound)) {
				//add fs to set
				this.modelStatus.add(node.get("id").toString());
			}
		}
	}
	
	//counts fs that have no owner persona
	void filterOwnerPersona() {
		//iterate through edges
		for (Map<String, Object> edge : this.currentList) {
			//get node from each edge
			Map<String, Object> node = (Map<String, Object>) edge.get("node");

			//var to hold number of owners
			int numOwners = 0;
			//search key to get user group list
			String searchKey = "rel" + node.get("type").toString() + "ToUserGroup";
			//use the key to get relation object
			Map<String, ArrayList> relation = (Map<String, ArrayList>) node.get(searchKey);
			//get list of user group relations from object
			ArrayList innerEdges = relation.get("edges");
			//go through edges
			for (Object innerEdge : innerEdges) {
				//get node of each edge
				Map<String, Object> currentInnerEdge = (Map<String, Object>) innerEdge;
				Map<String, Object> currentInnerNode = (Map<String, Object>) currentInnerEdge.get("node");
				//if there's a usage type and the type is owner
				if ((currentInnerNode.get("usageType") != null)
								&& currentInnerNode.get("usageType").toString().equals("owner")) {
					//increment the number of owners
					numOwners++;
				}
			}

			//if there's no owner
			if (numOwners == 0) {
				//add node to set
				this.ownerPersona.add(node.get("id").toString());
			}
		}
	}

	//counts fs with a score lower than the given double
	void filterScore(double percent) {
		//iterate through edges
		for (Map<String, Object> edge : this.currentList) {
			//get node from each edge
			Map<String, Object> node = (Map<String, Object>) edge.get("node");
			//get the completion object from each node
			Map<String, Double> innerNode = (Map<String, Double>) node.get("completion");
			//if the completion is less than the given double
			if (innerNode.get("completion") < percent) {
				//add fs to set
				this.score.add(node.get("id").toString());
			}
		}
	}

	//counts fs with no provided behaviors
	void filterProvidedBehaviorRelation() {
		//iterate through edges
		for (Map<String, Object> edge : this.currentList) {
			//get node from each edge
			Map<String, Object> node = (Map<String, Object>) edge.get("node");
			//key to get relations
			String searchKey = "relProvider" + node.get("type").toString() + "ToInterface";
			//get relations using key
			Map<String, Integer> innerNode = (Map<String, Integer>) node.get(searchKey);
			//if there are no provided behaviors
			if (innerNode.get("totalCount") == 0) {
				//add node to set
				this.relation.add(node.get("id").toString());
			}
		}
	}
	
	//count fs with no document links
	void filterDocuments() {
		//iterate through edges
		for (Map<String, Object> edge : this.currentList) {
			//get node from each edge
			Map<String, Object> node = (Map<String, Object>) edge.get("node");
			//get documents object
			Map<String, Integer> innerNode = (Map<String, Integer>) node.get("documents");
			//if there are no document links
			if (innerNode.get("totalCount") == 0) {
				//add fs to set
				this.documents.add(node.get("id").toString());
			}
		}
	}
	
	//counts fs with no lifecycle
	void filterLifecycle() {
		//iterate through edges
		for (Map<String, Object> edge : this.currentList) {
			//get node from each edge
			Map<String, Object> node = (Map<String, Object>) edge.get("node");
			//if there's no lifecycle
			if (node.get("lifecycle") == null) {
				//add fs to set
				this.lifecycle.add(node.get("id").toString());
				//else there is a lifecycle
			}
			else {
				//get lifecycle object from node
				Map<String, ArrayList> innerNode = (Map<String, ArrayList>) node.get("lifecycle");
				//get list of phases in lifecycle
				ArrayList ar = innerNode.get("phases");
				//if there are now phases, it's an empty lifecycle
				if (ar.size() == 0) {
					//add fs to set
					this.lifecycle.add(node.get("id").toString());
				}
			}
		}
	}
	
	//counts fs with no IT component of type software
	void filterSoftwareITRelation() {
		//iterate through edges
		for (Map<String, Object> edge : this.currentList) {
			//get node from each edge
			Map<String, Object> node = (Map<String, Object>) edge.get("node");

			//bool to see if software type is found
			boolean softwareFound = false;
			//key to get relation from node
			String searchKey = "rel" + node.get("type").toString() + "ToITComponent";
			//get relations using key
			Map<String, ArrayList> relation = (Map<String, ArrayList>) node.get(searchKey);
			//get the list of relation edges
			ArrayList<Map<String, Object>> innerEdges = relation.get("edges");
			//go through edges
			for (Map<String, Object> currentInnerEdge : innerEdges) {
				//get node from each edge
				Map<String, Object> currentInnerNode = (Map<String, Object>) currentInnerEdge.get("node");

				//get the it component factsheet
				Map<String, String> currentInnerFactsheet = (Map<String, String>) currentInnerNode.get("factSheet");
				//if the it component has a category and it software
				if ((currentInnerFactsheet.get("category") != null) &&
								(currentInnerFactsheet.get("category").equals("software"))) {
					//there is a software it component
					softwareFound = true;
					break;
				}
			}

			//if there's no software it component 
			if (!(softwareFound)) {
				//add fs to set
				this.relation.add(node.get("id").toString());
			}
		}
	}

	//counts fs with no business value or risk
	void filterBusinessValueRisk() {
		//iterate through edges
		for (Map<String, Object> edge : this.currentList) {
			//get node from each edge
			Map<String, Object> node = (Map<String, Object>) edge.get("node");

			//if there's no business value
			if (node.get("businessValue") == null) {
				//add fs to set
				this.businessValueRisk.add(node.get("id").toString());
			}
			//else if there's no project risk
			else if (node.get("projectRisk") == null) {
				//add fs to set
				this.businessValueRisk.add(node.get("id").toString());
			}
		}
	}
}
