package org.sunbird;

import org.sunbird.actor.core.ActorConfig;
import org.sunbird.common.ElasticSearchHelper;
import org.sunbird.common.factory.EsClientFactory;
import org.sunbird.common.inf.ElasticSearchService;
import org.sunbird.request.Request;
import org.sunbird.response.Response;

import java.util.Map;

@ActorConfig(
        tasks = {"echo"},
        dispatcher = "",
        asyncTasks = {}
)
public class Certification extends BaseActor {
    private static ElasticSearchService elasticSearchService= EsClientFactory.getInstance();
    @Override
    public void onReceive(Request request) throws Throwable {
        Response response = new Response();
        response.put("Response",request.getRequest());
        Map<String,Object> responseMap= (Map<String, Object>) ElasticSearchHelper.getResponseFromFuture(elasticSearchService.getDataByIdentifier("user","39745fe5-84d4-4498-966d-e20c46fb26fa"));
        response.put("data",responseMap);
        sender().tell(response,self());
    }
}
