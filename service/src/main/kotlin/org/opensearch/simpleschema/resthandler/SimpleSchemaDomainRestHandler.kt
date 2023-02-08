/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.opensearch.simpleschema.resthandler

import org.opensearch.client.node.NodeClient
import org.opensearch.commons.utils.logger
import org.opensearch.simpleschema.SimpleSchemaPlugin.Companion.BASE_SIMPLESCHEMA_URI
import org.opensearch.simpleschema.SimpleSchemaPlugin.Companion.LOG_PREFIX
import org.opensearch.simpleschema.model.RestTag.FROM_INDEX_FIELD
import org.opensearch.simpleschema.model.RestTag.MAX_ITEMS_FIELD
import org.opensearch.simpleschema.model.RestTag.OBJECT_ID_FIELD
import org.opensearch.simpleschema.model.RestTag.OBJECT_ID_LIST_FIELD
import org.opensearch.simpleschema.model.RestTag.OBJECT_TYPE_FIELD
import org.opensearch.simpleschema.model.RestTag.SORT_FIELD_FIELD
import org.opensearch.simpleschema.model.RestTag.SORT_ORDER_FIELD
import org.opensearch.simpleschema.util.contentParserNextToken
import org.opensearch.rest.BaseRestHandler
import org.opensearch.rest.BaseRestHandler.RestChannelConsumer
import org.opensearch.rest.BytesRestResponse
import org.opensearch.rest.RestHandler.Route
import org.opensearch.rest.RestRequest
import org.opensearch.rest.RestRequest.Method.GET
import org.opensearch.rest.RestRequest.Method.POST
import org.opensearch.rest.RestStatus
import org.opensearch.simpleschema.action.CreateSimpleSchemaDomainAction
import org.opensearch.simpleschema.action.CreateSimpleSchemaDomainRequest
import org.opensearch.simpleschema.action.GetSimpleSchemaDomainAction
import org.opensearch.simpleschema.action.GetSimpleSchemaDomainRequest
import org.opensearch.simpleschema.action.GetDomainOntologyAction
import org.opensearch.simpleschema.action.GetDomainOntologyRequest
import org.opensearch.simpleschema.action.GetDomainIndexAction
import org.opensearch.simpleschema.action.GetDomainIndexRequest
import org.opensearch.simpleschema.action.DomainGraphQLAction
import org.opensearch.simpleschema.action.DomainGraphQLRequest


/**
 * Rest handler for SimpleSchema object lifecycle management.
 * This handler uses [SimpleSchemaActions].
 */
internal class SimpleSchemaDomainRestHandler : BaseRestHandler() {
    companion object {
        private const val SIMPLESCHEMA_ACTION = "simpleschema_domain_actions"
        private const val SIMPLESCHEMA_URL = "$BASE_SIMPLESCHEMA_URI/domain"
        private val log by logger(SimpleSchemaDomainRestHandler::class.java)
    }

    /**
     * {@inheritDoc}
     */
    override fun getName(): String {
        return SIMPLESCHEMA_ACTION
    }

    /**
     * {@inheritDoc}
     */
    override fun routes(): List<Route> {
        return listOf(
            /**
             * Create a new object
             * Request URL: POST SIMPLESCHEMA_URL
             * Request body: Ref [org.opensearch.simpleschema.model.CreateSimpleSchemaDomainRequest]
             * Response body: Ref [org.opensearch.simpleschema.model.CreateSimpleSchemaDomainResponse]
             */
            Route(POST, SIMPLESCHEMA_URL),
            /**
             * Get an object
             * Request URL: GET SIMPLESCHEMA_URL/{objectId}
             * Request body: Ref [org.opensearch.simpleschema.model.GetSimpleSchemaDomainRequest]
             * Response body: Ref [org.opensearch.simpleschema.model.GetSimpleSchemadomainResponse]
             */
            Route(GET, "$SIMPLESCHEMA_URL/{$OBJECT_ID_FIELD}"),
            Route(GET, SIMPLESCHEMA_URL),
            /**
             * Get an object
             * Request URL: GET SIMPLESCHEMA_URL/{objectId}
             * Request body: Ref [org.opensearch.simpleschema.model.GetSimpleSchemaDomainRequest]
             * Response body: Ref [org.opensearch.simpleschema.model.GetSimpleSchemadomainResponse]
             */
            Route(GET, "$SIMPLESCHEMA_URL/{$OBJECT_ID_FIELD}/ontology"),
            /**
             * Get an object
             * Request URL: GET SIMPLESCHEMA_URL/{objectId}
             * Request body: Ref [org.opensearch.simpleschema.model.GetSimpleSchemaDomainRequest]
             * Response body: Ref [org.opensearch.simpleschema.model.GetSimpleSchemadomainResponse]
             */
            Route(GET, "$SIMPLESCHEMA_URL/{$OBJECT_ID_FIELD}/index"),
            /**
             * Get an object
             * Request URL: GET SIMPLESCHEMA_URL/{objectId}
             * Request body: Ref [org.opensearch.simpleschema.model.GetSimpleSchemaDomainRequest]
             * Response body: Ref [org.opensearch.simpleschema.model.GetSimpleSchemadomainResponse]
             */
            Route(POST, "$SIMPLESCHEMA_URL/{$OBJECT_ID_FIELD}/graphql")
        )
    }

    /**
     * {@inheritDoc}
     */
    override fun responseParams(): Set<String> {
        return setOf(
            OBJECT_ID_FIELD,
            OBJECT_ID_LIST_FIELD,
            OBJECT_TYPE_FIELD,
            SORT_FIELD_FIELD,
            SORT_ORDER_FIELD,
            FROM_INDEX_FIELD,
            MAX_ITEMS_FIELD
        )
    }

    private fun executePostRequest(request: RestRequest, client: NodeClient): RestChannelConsumer {
        return RestChannelConsumer {
            client.execute(
                CreateSimpleSchemaDomainAction.ACTION_TYPE,
                CreateSimpleSchemaDomainRequest.parse(request.contentParserNextToken()),
                RestResponseToXContentListener(it)
            )
        }
    }

    private fun executeGetRequest(request: RestRequest, client: NodeClient): RestChannelConsumer {
        val objectId: String = request.param(OBJECT_ID_FIELD)
        log.info(
            "$LOG_PREFIX:executeGetRequest $objectId"
        )
        return RestChannelConsumer {
            client.execute(
                GetSimpleSchemaDomainAction.ACTION_TYPE,
                GetSimpleSchemaDomainRequest(objectId),
                RestResponseToXContentListener(it)
            )
        }
    }

    private fun executeGetOntology(request: RestRequest, client: NodeClient): RestChannelConsumer {
        val objectId: String = request.param(OBJECT_ID_FIELD)
        log.info(
            "$LOG_PREFIX:executeGetOntology $objectId"
        )
        return RestChannelConsumer {
            client.execute(
                GetDomainOntologyAction.ACTION_TYPE,
                GetDomainOntologyRequest(objectId),
                RestResponseToXContentListener(it)
            )
        }
    }

    private fun executeGetIndex(request: RestRequest, client: NodeClient): RestChannelConsumer {
        val objectId: String = request.param(OBJECT_ID_FIELD)
        log.info(
            "$LOG_PREFIX:executeGetIndex $objectId"
        )
        return RestChannelConsumer {
            client.execute(
                GetDomainIndexAction.ACTION_TYPE,
                GetDomainIndexRequest(objectId),
                RestResponseToXContentListener(it)
            )
        }
    }

    private fun executeGraphQL(request: RestRequest, client: NodeClient): RestChannelConsumer {
        val objectId: String = request.param(OBJECT_ID_FIELD)
        log.info(
            "$LOG_PREFIX:executeGraphQL $objectId"
        )
        return RestChannelConsumer {
            client.execute(
                DomainGraphQLAction.ACTION_TYPE,
                DomainGraphQLRequest(objectId),
                RestResponseToXContentListener(it)
            )
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun prepareRequest(request: RestRequest, client: NodeClient): RestChannelConsumer {
        return when (request.method()) {
            POST -> executePostRequest(request, client)
            GET -> when (request.path().split("/").last()){
                "ontology" -> executeGetOntology(request, client)
                "index" -> executeGetIndex(request, client)
                "graphql" -> executeGraphQL(request, client)
                else -> executeGetRequest(request, client)
            }
            else -> RestChannelConsumer {
                it.sendResponse(BytesRestResponse(RestStatus.METHOD_NOT_ALLOWED, "${request.method()} is not allowed"))
            }
        }
    }
}
