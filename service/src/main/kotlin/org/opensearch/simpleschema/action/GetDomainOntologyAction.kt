package org.opensearch.simpleschema.action

import org.opensearch.action.ActionType
import org.opensearch.action.support.ActionFilters
import org.opensearch.client.Client
import org.opensearch.common.inject.Inject
import org.opensearch.common.xcontent.NamedXContentRegistry
import org.opensearch.commons.authuser.User
import org.opensearch.transport.TransportService

internal class GetDomainOntologyAction @Inject constructor(
    transportService: TransportService,
    client: Client,
    actionFilters: ActionFilters,
    val xContentRegistry: NamedXContentRegistry
) : PluginBaseAction<GetDomainOntologyRequest, GetDomainOntologyResponse>(
    NAME,
    transportService,
    client,
    actionFilters,
    ::GetDomainOntologyRequest
) {
    companion object {
        private const val NAME = "cluster:admin/opensearch/simpleschema/domain/ontology"
        internal val ACTION_TYPE = ActionType(NAME, ::GetDomainOntologyResponse)
    }

    /**
     * {@inheritDoc}
     */
    override fun executeRequest(
        request: GetDomainOntologyRequest,
        user: User?
    ): GetDomainOntologyResponse {
        return SimpleSchemaDomainActions.getOntology(request, user)
    }
}
