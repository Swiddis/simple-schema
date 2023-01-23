package org.opensearch.simpleschema.action

import org.opensearch.common.io.stream.StreamInput
import org.opensearch.common.io.stream.StreamOutput
import org.opensearch.common.xcontent.ToXContent
import org.opensearch.common.xcontent.XContentBuilder
import org.opensearch.simpleschema.model.BaseResponse

internal class GetDomainOntologyResponse : BaseResponse {
    constructor(input: StreamInput) {
        TODO("Not yet implemented")
    }

    override fun toXContent(builder: XContentBuilder?, params: ToXContent.Params?): XContentBuilder {
        TODO("Not yet implemented")
    }

    override fun writeTo(out: StreamOutput?) {
        TODO("Not yet implemented")
    }
}