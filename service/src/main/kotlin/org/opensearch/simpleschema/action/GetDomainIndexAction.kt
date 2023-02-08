package org.opensearch.simpleschema.action

import org.opensearch.action.ActionType
import org.opensearch.action.support.ActionFilters
import org.opensearch.client.Client
import org.opensearch.common.inject.Inject
import org.opensearch.common.xcontent.NamedXContentRegistry
import org.opensearch.commons.authuser.User
import org.opensearch.transport.TransportService

internal class GetDomainIndexAction @Inject constructor(
    transportService: TransportService,
    client: Client,
    actionFilters: ActionFilters,
    val xContentRegistry: NamedXContentRegistry
) : PluginBaseAction<GetDomainIndexRequest, GetDomainIndexResponse>(
    NAME,
    transportService,
    client,
    actionFilters,
    ::GetDomainIndexRequest
) {
    companion object {
        private const val NAME = "cluster:admin/opensearch/simpleschema/domain/index"
        internal val ACTION_TYPE = ActionType(NAME, ::GetDomainIndexResponse)
    }

    /**
     * {@inheritDoc}
     */
    override fun executeRequest(
        request: GetDomainIndexRequest,
        user: User?
    ): GetDomainIndexResponse {
        return SimpleSchemaDomainActions.getIndex(request, user)
    }
}
