package com.ibm.watson.conversationalskills.sdk;

import com.ibm.watson.conversationalskills.model.*;
import com.ibm.watson.conversationalskills.sdk.utils.BluePointsSkill;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SkillOrchestratorTest {

    private final SkillOrchestrator skillOrchestrator = new SkillOrchestrator();

    /**
     * Test orchestrate function
     */
    @Test
    void testOrchestrateEmptyResponse() throws Exception {
        var skill = new BluePointsSkill();
        var orchestrationResponse = skillOrchestrator.orchestrate(skill, null);
        assertEquals(orchestrationResponse, new OrchestrationResponse());
    }

    @Test
    void testOrchestrateCancelledEvent() throws Exception {
        var skill = new BluePointsSkill();
        var orchestrationRequest = new OrchestrationRequest();
        orchestrationRequest.setConfirmationEvent(OrchestrationRequest.ConfirmationEventEnum.CANCELLED);
        orchestrationRequest.setContext(new MessageContext());
        orchestrationRequest.setState(new ConversationalSkillStateInput());

        var orchestrationResponse = skillOrchestrator.orchestrate(skill, orchestrationRequest);
        assertEquals(orchestrationResponse.getOutput().getGeneric().get(0).getResponseType(), "text");
        assertEquals(orchestrationResponse.getOutput().getGeneric().get(0).getText(), "The skill was cancelled.");
        assertEquals(orchestrationResponse.getResolver().get("type"), OrchestrationResponseResolver.TypeEnum.SKILL_CANCEL.toString());
    }

    @Test
    void testOrchestrateConfirmedEvent() throws Exception {
        var skill = new BluePointsSkill();
        var orchestrationRequest = new OrchestrationRequest();
        orchestrationRequest.setConfirmationEvent(OrchestrationRequest.ConfirmationEventEnum.CONFIRMED);
        orchestrationRequest.setContext(new MessageContext());
        orchestrationRequest.setState(new ConversationalSkillStateInput());

        var orchestrationResponse = skillOrchestrator.orchestrate(skill, orchestrationRequest);

        assertEquals(orchestrationResponse.getOutput().getGeneric().get(0).getResponseType(), "text");
        assertEquals(orchestrationResponse.getOutput().getGeneric().get(0).getText(), "text");
        assertEquals(orchestrationResponse.getResolver().get("type"), OrchestrationResponseResolver.TypeEnum.SKILL_COMPLETE.toString());
    }

    @Test
    void testOrchestrateFillEvent() throws Exception {
        var skill = new BluePointsSkill();
        var orchestrationRequest = new OrchestrationRequest();
        orchestrationRequest.setContext(new MessageContext());
        orchestrationRequest.setState(new ConversationalSkillStateInput());

        var slotState = new SlotState()
                .name("amount")
                .event(SlotState.EventEnum.FILL)
                .value(new SlotValue().normalized("John").literal("John"));
        var slotStateArray = new ArrayList<SlotState>();
        slotStateArray.add(slotState);
        orchestrationRequest.setSlots(slotStateArray);

        var orchestrationResponse = skillOrchestrator.orchestrate(skill, orchestrationRequest);

        assertEquals(orchestrationResponse.getOutput().getGeneric().get(0).getResponseType(), "slots");
        assertEquals(orchestrationResponse.getState().getLocalVariables().get("amount"), "John");
        assertEquals(orchestrationResponse.getState().getLocalVariables().get("visible_slots"), new ArrayList<>(Arrays.asList("amount")));
        assertEquals(orchestrationResponse.getOutput().getGeneric().get(0).getConfirmation().getPrompt(), "not_yet");
        assertEquals(orchestrationResponse.getResolver().get("type"), OrchestrationResponseResolver.TypeEnum.USER_INTERACTION.toString());
    }

    @Test
    void testOrchestrateFillEventComplete() throws Exception {
        var skill = new BluePointsSkill();
        skill.setConfirmationMessage(null);

        var orchestrationRequest = new OrchestrationRequest();
        orchestrationRequest.setContext(new MessageContext());
        orchestrationRequest.setState(new ConversationalSkillStateInput());

        var slotState = new SlotState()
                .name("amount")
                .event(SlotState.EventEnum.FILL)
                .value(new SlotValue().normalized("John").literal("John"));
        var slotStateArray = new ArrayList<SlotState>();
        slotStateArray.add(slotState);
        orchestrationRequest.setSlots(slotStateArray);

        var orchestrationResponse = skillOrchestrator.orchestrate(skill, orchestrationRequest);

        assertEquals(orchestrationResponse.getOutput().getGeneric().get(0).getResponseType(), "text");
        assertEquals(orchestrationResponse.getResolver().get("type"), OrchestrationResponseResolver.TypeEnum.SKILL_COMPLETE.toString());
    }

    @Test
    void testOrchestrateValidationError() throws Exception {
        var skill = new BluePointsSkill();
        skill.setAmountSlotHandlerValidationError("There was a validation error");

        var orchestrationRequest = new OrchestrationRequest();
        orchestrationRequest.setContext(new MessageContext());
        orchestrationRequest.setState(new ConversationalSkillStateInput());

        var slotState = new SlotState()
                .name("amount")
                .event(SlotState.EventEnum.FILL)
                .value(new SlotValue().normalized("John").literal("John"));
        var slotStateArray = new ArrayList<SlotState>();
        slotStateArray.add(slotState);
        orchestrationRequest.setSlots(slotStateArray);

        var orchestrationResponse = skillOrchestrator.orchestrate(skill, orchestrationRequest);

        assertEquals(orchestrationResponse.getOutput().getGeneric().get(0).getResponseType(), "slots");
        assertEquals(orchestrationResponse.getState().getLocalVariables().get("amount"), "John");
        assertEquals(orchestrationResponse.getState().getLocalVariables().get("visible_slots"), new ArrayList<>(Arrays.asList("amount")));
        assertEquals(orchestrationResponse.getOutput().getGeneric().get(0).getConfirmation(), null);
        assertEquals(orchestrationResponse.getResolver().get("type"), OrchestrationResponseResolver.TypeEnum.USER_INTERACTION.toString());
    }

}
