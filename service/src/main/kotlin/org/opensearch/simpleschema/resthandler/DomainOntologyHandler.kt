package org.opensearch.simpleschema.resthandler

import org.opensearch.client.node.NodeClient
import org.opensearch.commons.utils.logger
import org.opensearch.rest.*
import org.opensearch.simpleschema.SimpleSchemaPlugin
import org.opensearch.simpleschema.action.CreateSimpleSchemaDomainAction
import org.opensearch.simpleschema.action.CreateSimpleSchemaDomainRequest
import org.opensearch.simpleschema.action.GetSimpleSchemaDomainAction
import org.opensearch.simpleschema.action.GetSimpleSchemaDomainRequest
import org.opensearch.simpleschema.model.RestTag
import org.opensearch.simpleschema.util.contentParserNextToken

internal class DomainOntologyHandler : BaseRestHandler() {
    companion object {
        private const val SIMPLESCHEMA_ACTION = "simpleschema_domain_ontology_actions"
        private const val SIMPLESCHEMA_URL = "${SimpleSchemaPlugin.BASE_SIMPLESCHEMA_URI}/domain/{${RestTag.OBJECT_ID_FIELD}}/ontology"
        private val log by logger(DomainOntologyHandler::class.java)
    }

    override fun getName(): String {
        return SIMPLESCHEMA_ACTION
    }

    /**
     * {@inheritDoc}
     */
    override fun routes(): List<RestHandler.Route> {
        return listOf(
            /**
             * Get an object
             * Request URL: GET SIMPLESCHEMA_URL/{objectId}
             * Request body: Ref [org.opensearch.simpleschema.model.GetSimpleSchemaDomainRequest]
             * Response body: Ref [org.opensearch.simpleschema.model.GetSimpleSchemadomainResponse]
             */
            RestHandler.Route(RestRequest.Method.GET, SIMPLESCHEMA_URL)
        )
    }

    /**
     * {@inheritDoc}
     */
    override fun responseParams(): Set<String> {
        return setOf(
            RestTag.OBJECT_ID_FIELD,
            RestTag.OBJECT_ID_LIST_FIELD,
            RestTag.OBJECT_TYPE_FIELD,
            RestTag.SORT_FIELD_FIELD,
            RestTag.SORT_ORDER_FIELD,
            RestTag.FROM_INDEX_FIELD,
            RestTag.MAX_ITEMS_FIELD
        )
    }

    private fun executeGetRequest(request: RestRequest, client: NodeClient): RestChannelConsumer {
        TODO("not implemented")
    }

    /**
     * {@inheritDoc}
     */
    override fun prepareRequest(request: RestRequest, client: NodeClient): RestChannelConsumer {
        return when (request.method()) {
            RestRequest.Method.GET -> executeGetRequest(request, client)
            else -> RestChannelConsumer {
                it.sendResponse(BytesRestResponse(RestStatus.METHOD_NOT_ALLOWED, "${request.method()} is not allowed"))
            }
        }
    }
}