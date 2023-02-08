package org.opensearch.simpleschema.action

import org.opensearch.action.ActionRequest
import org.opensearch.action.ActionRequestValidationException
import org.opensearch.common.io.stream.StreamInput
import org.opensearch.common.xcontent.ToXContent
import org.opensearch.common.xcontent.ToXContentObject
import org.opensearch.common.xcontent.XContentBuilder
import java.io.IOException

internal class GetDomainOntologyRequest : ActionRequest, ToXContentObject {
    val domainName: String

    constructor(domainName: String) {
        this.domainName = domainName
    }

    @Throws(IOException::class)
    constructor(input: StreamInput) : super(input) {
        domainName = input.readString()
    }

    override fun validate(): ActionRequestValidationException {
        TODO("Not yet implemented")
    }

    override fun toXContent(builder: XContentBuilder?, params: ToXContent.Params?): XContentBuilder {
        TODO("Not yet implemented")
    }
}
