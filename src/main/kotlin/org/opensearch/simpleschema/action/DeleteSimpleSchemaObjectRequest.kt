/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.simpleschema.action

import org.opensearch.action.ActionRequest
import org.opensearch.action.ActionRequestValidationException
import org.opensearch.action.ValidateActions
import org.opensearch.common.io.stream.StreamInput
import org.opensearch.common.io.stream.StreamOutput
import org.opensearch.common.io.stream.Writeable
import org.opensearch.common.xcontent.ToXContent
import org.opensearch.common.xcontent.ToXContentObject
import org.opensearch.common.xcontent.XContentBuilder
import org.opensearch.common.xcontent.XContentParser
import org.opensearch.common.xcontent.XContentParserUtils
import org.opensearch.commons.utils.logger
import org.opensearch.commons.utils.stringList
import org.opensearch.simpleschema.SimpleSchemaPlugin.Companion.LOG_PREFIX
import org.opensearch.simpleschema.model.RestTag.OBJECT_ID_FIELD
import org.opensearch.simpleschema.model.RestTag.OBJECT_ID_LIST_FIELD
import java.io.IOException

/**
 * Action request for creating new configuration.
 */
internal class DeleteSimpleSchemaObjectRequest : ActionRequest, ToXContentObject {
    val objectIds: Set<String>

    companion object {
        private val log by logger(DeleteSimpleSchemaObjectRequest::class.java)

        /**
         * reader to create instance of class from writable.
         */
        val reader = Writeable.Reader { DeleteSimpleSchemaObjectRequest(it) }

        /**
         * Creator used in REST communication.
         * @param parser XContentParser to deserialize data from.
         * @param id optional id to use if missed in XContent
         */
        @JvmStatic
        @Throws(IOException::class)
        fun parse(parser: XContentParser): DeleteSimpleSchemaObjectRequest {
            var objectIds: Set<String>? = null

            XContentParserUtils.ensureExpectedToken(
                XContentParser.Token.START_OBJECT,
                parser.currentToken(),
                parser
            )
            while (parser.nextToken() != XContentParser.Token.END_OBJECT) {
                val fieldName = parser.currentName()
                parser.nextToken()
                when (fieldName) {
                    OBJECT_ID_LIST_FIELD -> objectIds = parser.stringList().toSet()
                    else -> {
                        parser.skipChildren()
                        log.info("$LOG_PREFIX:Skipping Unknown field $fieldName")
                    }
                }
            }
            objectIds ?: throw IllegalArgumentException("$OBJECT_ID_FIELD field absent")
            return DeleteSimpleSchemaObjectRequest(objectIds)
        }
    }

    /**
     * constructor for creating the class
     * @param objectIds the id of the SimpleSchema object
     */
    constructor(objectIds: Set<String>) {
        this.objectIds = objectIds
    }

    /**
     * {@inheritDoc}
     */
    @Throws(IOException::class)
    constructor(input: StreamInput) : super(input) {
        objectIds = input.readStringList().toSet()
    }

    /**
     * {@inheritDoc}
     */
    @Throws(IOException::class)
    override fun writeTo(output: StreamOutput) {
        super.writeTo(output)
        output.writeStringCollection(objectIds)
    }

    /**
     * {@inheritDoc}
     */
    override fun toXContent(builder: XContentBuilder?, params: ToXContent.Params?): XContentBuilder {
        builder!!
        return builder.startObject()
            .field(OBJECT_ID_LIST_FIELD, objectIds)
            .endObject()
    }

    /**
     * {@inheritDoc}
     */
    override fun validate(): ActionRequestValidationException? {
        var validationException: ActionRequestValidationException? = null
        if (objectIds.isNullOrEmpty()) {
            validationException = ValidateActions.addValidationError("objectIds is null or empty", validationException)
        }
        return validationException
    }
}
