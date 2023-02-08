package org.opensearch.simpleschema.action

import org.opensearch.action.ActionType
import org.opensearch.action.support.ActionFilters
import org.opensearch.client.Client
import org.opensearch.common.inject.Inject
import org.opensearch.common.xcontent.NamedXContentRegistry
import org.opensearch.commons.authuser.User
import org.opensearch.transport.TransportService

internal class DomainGraphQLAction @Inject constructor(
    transportService: TransportService,
    client: Client,
    actionFilters: ActionFilters,
    val xContentRegistry: NamedXContentRegistry
) : PluginBaseAction<DomainGraphQLRequest, DomainGraphQLResponse>(
    NAME,
    transportService,
    client,
    actionFilters,
    ::DomainGraphQLRequest
) {
    companion object {
        private const val NAME = "cluster:admin/opensearch/simpleschema/domain/graphql"
        internal val ACTION_TYPE = ActionType(NAME, ::DomainGraphQLResponse)
    }

    /**
     * {@inheritDoc}
     */
    override fun executeRequest(
        request: DomainGraphQLRequest,
        user: User?
    ): DomainGraphQLResponse {
        return SimpleSchemaDomainActions.graphql(request, user)
    }
}
