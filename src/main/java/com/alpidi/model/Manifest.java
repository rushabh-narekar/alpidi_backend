package com.alpidi.model;

import java.util.Map;

import com.alpidi.exception.APIConnectionException;
import com.alpidi.exception.APIException;
import com.alpidi.exception.AuthenticationException;
import com.alpidi.exception.InvalidRequestException;
import com.alpidi.net.APIResource;

public class Manifest extends APIResource {
	String objectState;
	String status;
	String objectId;
	String objectOwner;
	Object objectCreated;
	Object objectUpdated;
    Object provider;
    Object shipmentDate;
    Object addressFrom;
    Object documents;

    public static Manifest create(Map<String, Object> params) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return create(params, null);
    }

    public String getInstanceURL() {
        return "";
    }

    public static Manifest create(Map<String, Object> params, String apiKey) throws AuthenticationException,
            InvalidRequestException, APIConnectionException, APIException {
        return request(RequestMethod.POST, classURL(Manifest.class), params, Manifest.class, apiKey);
    }

    public static Manifest retrieve(String id) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return retrieve(id, null);
    }

    public static Manifest retrieve(String id, String apiKey) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return request(RequestMethod.GET, instanceURL(Manifest.class, id), null, Manifest.class, apiKey);
    }

    public static ManifestCollection all(Map<String, Object> params) throws AuthenticationException,
            InvalidRequestException, APIConnectionException, APIException {
        return all(params, null);
    }

    public static ManifestCollection all(Map<String, Object> params, String apiKey) throws AuthenticationException,
            InvalidRequestException, APIConnectionException, APIException {
        return request(RequestMethod.GET, classURL(Manifest.class), params, ManifestCollection.class, apiKey);
    }

	public String getObjectState() {
		return objectState;
	}

	public void setObjectState(String objectState) {
		this.objectState = objectState;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectOwner() {
		return objectOwner;
	}

	public void setObjectOwner(String objectOwner) {
		this.objectOwner = objectOwner;
	}

	public Object getObjectCreated() {
		return objectCreated;
	}

	public void setObjectCreated(Object objectCreated) {
		this.objectCreated = objectCreated;
	}

	public Object getObjectUpdated() {
		return objectUpdated;
	}

	public void setObjectUpdated(Object objectUpdated) {
		this.objectUpdated = objectUpdated;
	}

	public Object getProvider() {
		return provider;
	}

	public void setProvider(Object provider) {
		this.provider = provider;
	}

	public Object getShipmentDate() {
		return shipmentDate;
	}

	public void setShipmentDate(Object shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	public Object getAddressFrom() {
		return addressFrom;
	}

	public void setAddressFrom(Object addressFrom) {
		this.addressFrom = addressFrom;
	}

	public Object getDocuments() {
		return documents;
	}

	public void setDocuments(Object documents) {
		this.documents = documents;
	}

}