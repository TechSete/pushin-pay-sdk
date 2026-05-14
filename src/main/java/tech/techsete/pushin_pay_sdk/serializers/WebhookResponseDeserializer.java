package tech.techsete.pushin_pay_sdk.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import tech.techsete.pushin_pay_sdk.dtos.response.WebhookResponse;

import java.io.IOException;

public class WebhookResponseDeserializer extends JsonDeserializer<WebhookResponse> {

    @Override
    public WebhookResponse deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);

        if (node == null || node.isNull() || node.isMissingNode()) {
            return null;
        }

        if (node.isArray()) {
            if (node.isEmpty()) {
                return null;
            }

            JsonNode firstWebhook = node.get(0);
            if (firstWebhook == null || firstWebhook.isNull()) {
                return null;
            }

            return parser.getCodec().treeToValue(firstWebhook, WebhookResponse.class);
        }

        if (node.isObject()) {
            return parser.getCodec().treeToValue(node, WebhookResponse.class);
        }

        return null;
    }
}
