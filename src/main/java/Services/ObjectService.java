package Services;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ObjectService extends Service {
    public String OBJECTS_BASE_PATH = "objects";
    public String OBJECTS_BASE_PATH_BY_IDS = "objects?id=3&id=5&id=10";
    public String OBJECT_BASE_PATH = "objects/7";
    public String UPDATE_OBJECT_BASE_PATH = "objects/ff8081819782e69e019a3c911c9f1e63";
    public String DELETE_OBJECT_BASE_PATH = "objects/ff8081819782e69e019a3c9620a31e67";
    private static final Logger logger = LogManager.getLogger(ObjectService.class);
    public ObjectService(RequestSpecification httpRequest) {
        super(httpRequest);
    }

    public Response getAllObjects() {
        logger.info("Start making API Get call");
        return httpRequest.request().get(OBJECTS_BASE_PATH);
    }

    public Response getAllObjectsByIDS() {
        return httpRequest.request().get(OBJECTS_BASE_PATH_BY_IDS);
    }

    public Response getObject() {
        return httpRequest.request().get(OBJECT_BASE_PATH);
    }

    public Response addObject(String body) {
        return httpRequest.contentType(ContentType.JSON).body(body).post(OBJECTS_BASE_PATH);
    }

    public Response updateObject(String body) {
        return httpRequest.contentType(ContentType.JSON).body(body).put(UPDATE_OBJECT_BASE_PATH);
    }

    public Response patchObject(String body) {
        return httpRequest.contentType(ContentType.JSON).body(body).patch(UPDATE_OBJECT_BASE_PATH);
    }

    public Response deleteObject() {
        return httpRequest.contentType(ContentType.JSON).delete(DELETE_OBJECT_BASE_PATH);
    }
}
