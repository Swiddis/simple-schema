package org.opensearch.simpleschema.action

import org.opensearch.action.ActionRequest
import org.opensearch.action.ActionRequestValidationException
import org.opensearch.common.io.stream.StreamInput
import org.opensearch.common.xcontent.ToXContent
import org.opensearch.common.xcontent.ToXContentObject
import org.opensearch.common.xcontent.XContentBuilder

internal class GetDomainOntologyRequest : ActionRequest, ToXContentObject {
    constructor(input: StreamInput) {
        TODO("Not yet implemented")
    }

    override fun validate(): ActionRequestValidationException {
        TODO("Not yet implemented")
    }

    override fun toXContent(builder: XContentBuilder?, params: ToXContent.Params?): XContentBuilder {
        TODO("Not yet implemented")
    }
}