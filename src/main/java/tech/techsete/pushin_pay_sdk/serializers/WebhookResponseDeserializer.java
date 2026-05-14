package tech.techsete.pushin_pay_sdk.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import tech.techsete.pushin_pay_sdk.dtos.response.WebhookResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WebhookResponseDeserializer extends JsonDeserializer<Collection<WebhookResponse>> {

    @Override
    public Collection<WebhookResponse> deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);

        if (node == null || node.isNull() || node.isMissingNode()) {
            return null;
        }

        if (node.isArray()) {
            if (node.isEmpty()) {
                return List.of();
            }

            Collection<WebhookResponse> webhooks = new ArrayList<>();
            for (JsonNode webhookNode : node) {
                if (webhookNode != null && !webhookNode.isNull()) {
                    webhooks.add(parser.getCodec().treeToValue(webhookNode, WebhookResponse.class));
                }
            }

            return webhooks;
        }

        if (node.isObject()) {
            return List.of(parser.getCodec().treeToValue(node, WebhookResponse.class));
        }

        return List.of();
    }
}
