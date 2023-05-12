package ibf2022.batch2.csf.backend.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.batch2.csf.backend.models.Bundle;

@Repository
public class ArchiveRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	private final String C_ARCHIVE = "archives";

	//TODO: Task 4
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	/*
	 * db.archives.insert(
		{
			"bundleId" : "723285b3",
			"date" : ISODate("2023-05-12T05:07:33.749+0000"),
			"title" : "test",
			"name" : "test",
			"comments" : "test",
			"urls" : [
				"https://leem0060-csf-assessment.sgp1.digitaloceanspaces.com/number0.jpg",
				"https://leem0060-csf-assessment.sgp1.digitaloceanspaces.com/number14.jpg"
			]
		}
	)
	 */
	public void recordBundle(Bundle b) {

		Document toInsert = b.toDocument();
		mongoTemplate.insert(toInsert, C_ARCHIVE);
		
	}

	//TODO: Task 5
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	/*
	 * db.archives.findOne(
			{ bundleId: "723285b3"}
		)
	 */
	public Document getBundleByBundleId(String bundleId) {

		Query query = Query.query(Criteria.where("bundleId").is(bundleId));
		Document doc = mongoTemplate.findOne(query, Document.class, C_ARCHIVE); // return null if not found
		return doc;
	}

	//TODO: Task 6
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	public Object getBundles(/* any number of parameters here */) {
		return null;
	}


}
