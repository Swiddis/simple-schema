package org.opensearch.simpleschema.rest

import org.junit.Assert
import org.opensearch.rest.RestRequest
import org.opensearch.rest.RestStatus
import org.opensearch.simpleschema.*
import org.opensearch.simpleschema.SimpleSchemaPlugin.Companion.BASE_SIMPLESCHEMA_URI

class CreateSchemaIT : PluginRestTestCase() {
    private val SCHEMA_OBJECTS = mapOf(
        Pair("null", ""),
        Pair("book_malformatted", "type Book{\n  title: String"),
        Pair("book_one_field", "type Book {\n  title: String\n}")
    )

    private fun createSchemaRequestContent(schemaObjects: List<String>): String {
        return schemaObjects.joinToString(separator = "\n\n") { SCHEMA_OBJECTS[it].toString() }
    }

    private fun createSchemaRequestJson(contentSchemaObjects: List<String>): String {
        val content = createSchemaRequestContent(contentSchemaObjects)
        return """
            {
                "simpleSchema": {
                    "name": "test_schema",
                    "content": "$content"
                }
            }
        """.trimIndent()
    }

    fun `test create empty schema`() {
        val createEmptySchemaResponse = executeRequest(
            RestRequest.Method.POST.name,
            "${BASE_SIMPLESCHEMA_URI}/schema",
            createSchemaRequestJson(listOf("null")),
            RestStatus.BAD_REQUEST.status
        )
        validateErrorResponse(createEmptySchemaResponse, RestStatus.BAD_REQUEST.status, "parse_exception")
        Thread.sleep(100)
    }

    fun `test create one-object schema`() {
        val createSchemaResponse = executeRequest(
            RestRequest.Method.POST.name,
            "${BASE_SIMPLESCHEMA_URI}/schema",
            createSchemaRequestJson(listOf("book_one_field")),
            RestStatus.BAD_REQUEST.status
        )
        val id = createSchemaResponse.get("schemaId").asString
        Assert.assertNotNull("Schema ID should be generated", id)
        Thread.sleep(100)
    }

    fun `test create invalid schema`() {
        val createMalformattedSchemaResponse = executeRequest(
            RestRequest.Method.POST.name,
            "${BASE_SIMPLESCHEMA_URI}/schema",
            createSchemaRequestJson(listOf("book_malformatted")),
            RestStatus.BAD_REQUEST.status
        )
        validateErrorResponse(createMalformattedSchemaResponse, RestStatus.BAD_REQUEST.status, "parse_exception")
        Thread.sleep(100)
    }
}