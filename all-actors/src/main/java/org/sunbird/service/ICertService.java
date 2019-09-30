package org.sunbird.service;


import org.sunbird.BaseException;
import org.sunbird.request.Request;

/**
 * this is an interface class for implementing certificate related operations
 *
 */
public interface ICertService{

    String add(Request request) throws BaseException;

}
