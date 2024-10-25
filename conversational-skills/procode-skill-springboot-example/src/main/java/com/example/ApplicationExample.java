package com.example;

import com.example.bluepoints.BluePointsSkill;
import com.ibm.watson.conversationalskills.model.*;
import com.ibm.watson.conversationalskills.sdk.Skill;
import com.ibm.watson.conversationalskills.sdk.SkillOrchestrator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@SpringBootApplication
public class ApplicationExample {

	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}

	/**
     * GET /providers/{provider_id}/conversational_skills : Fetch a list of conversational skills
     * Retrieves a list of conversational skills associated to a particular provider.
     *
     * @param providerId Unique identifier of the provider that possesses the conversational skill. It represents the instance that is linked with the WxA instance. (required)
     * @param assistantId Assistant ID values that need to be considered for filtering (required)
     * @param environmentId Environment ID values that need to be considered for filtering (required)
     * @return Successful request. (status code 200)
     *         or Invalid request. (status code 400)
     *         or Internal error. (status code 500)
     */
    @Operation(
        operationId = "fetchSkills",
        summary = "Fetch a list of conversational skills",
        description = "Retrieves a list of conversational skills associated to a particular provider.",
        tags = { "Conversational skill" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful request.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ListSkillsResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/providers/{provider_id}/conversational_skills",
        produces = { "application/json" }
    )
    
    ResponseEntity<ListSkillsResponse> fetchSkills(
        @PathVariable(value = "provider_id", required = true) String providerId,
        @RequestParam(value = "assistant_id", required = true, defaultValue = "available") String assistantId,
        @RequestParam(value = "environment_id", required = true, defaultValue = "available") String environmentId
    ) {
        // Initialize skill
        var skill = new BluePointsSkill();
        // Format skill for response object
        var formattedSkill = skill.formatForListSkills();

        // Add skill to response object
        var listSkillsResponse = new ListSkillsResponse();
        listSkillsResponse.addConversationalSkillsItem(formattedSkill);
        // If you are adding more skills ex:
        // listSkillsResponse.addConversationalSkillsItem(formattedSkill2);
        // listSkillsResponse.addConversationalSkillsItem(formattedSkill3);

        return new ResponseEntity<>(listSkillsResponse, HttpStatus.OK);
    }

    /**
     * GET /providers/{provider_id}/conversational_skills/{conversational_skill_id} : Get a conversational skill
     * Get a conversational skill associated to a particular provider.
     *
     * @param providerId Unique identifier of the provider that possesses the conversational skill. It represents the instance that is linked with the WxA instance. (required)
     * @param conversationalSkillId Unique identifier of the conversational skill. (required)
     * @param assistantId Assistant ID values that need to be considered for filtering (required)
     * @param environmentId Environment ID values that need to be considered for filtering (required)
     * @return Successful request. (status code 200)
     *         or Invalid request. (status code 400)
     *         or Internal error. (status code 500)
     */
    @Operation(
            operationId = "getSkill",
            summary = "Get a conversational skill",
            description = "Get a conversational skill associated to a particular provider.",
            tags = { "Conversational skill" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful request.", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = GetSkillResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid request.", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal error.", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/providers/{provider_id}/conversational_skills/{conversational_skill_id}",
            produces = { "application/json" }
    )

    ResponseEntity<GetSkillResponse> getSkill(
            @PathVariable(value = "provider_id", required = true) String providerId,
            @PathVariable(value = "conversational_skill_id", required = true) String conversationalSkillId,
            @RequestParam(value = "assistant_id", required = true, defaultValue = "available") String assistantId,
            @RequestParam(value = "environment_id", required = true, defaultValue = "available") String environmentId
    ) {
        // Initialize skill
        var skill = new BluePointsSkill();
        // Format skill for response object
        var formattedSkill = skill.formatForGetSkill();

        return new ResponseEntity<>(formattedSkill, HttpStatus.OK);
    }

    /**
     * POST /providers/{provider_id}/conversational_skills/{conversational_skill_id}/orchestrate : Orchestrate a conversation
     * Sends user input along with conversation state (including slots and other context data) stored by watsonx Assistant, and the current turn output, to the conversational skill, to let it run its business logic and tell watsonx Assistant what to do next.
     *
     * @param providerId Unique identifier of the provider that possesses the conversational skill. It represents the instance that is linked with the WxA instance. (required)
     * @param conversationalSkillId Unique identifier of the conversational skill. It represents business logic to orchestrate a specific conversation. (required)
     * @param orchestrationRequest The request to be sent to conversational skill. This includes the user&#39;s input (relayed from WxA /message API), WxA context, slots value state, and conversational skill state. (optional)
     * @return Successful request. (status code 200)
     *         or Invalid request. (status code 400)
     */
    @Operation(
            operationId = "orchestrate",
            summary = "Orchestrate a conversation",
            description = "Sends user input along with conversation state (including slots and other context data) stored by watsonx Assistant, and the current turn output, to the conversational skill, to let it run its business logic and tell watsonx Assistant what to do next.",
            tags = { "Conversational skill" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful request.", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = OrchestrationResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid request.", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/providers/{provider_id}/conversational_skills/{conversational_skill_id}/orchestrate",
            produces = { "application/json" },
            consumes = { "application/json" }
    )

    ResponseEntity<OrchestrationResponse> orchestrate(
            @PathVariable("provider_id") String providerId,
            @PathVariable("conversational_skill_id") String conversationalSkillId,
            @Valid @RequestBody(required = false) OrchestrationRequest orchestrationRequest
    ) throws Exception {
        Skill[] skills = {new BluePointsSkill()};

        // Ensure that skill with provided ID exists
        Skill skillWithID = null;
        for (var skill : skills) {
            if (skill.getID().equals(conversationalSkillId)) {
                skillWithID = skill;
            }
        }

        if (skillWithID == null) {
            throw new IllegalArgumentException("Skill with ID '"
                    + conversationalSkillId + "' does not exist");
        }

        var skillOrchestrator = new SkillOrchestrator();
        var orchestrationResponse = skillOrchestrator.orchestrate(skillWithID, orchestrationRequest);
        return new ResponseEntity<>(orchestrationResponse, HttpStatus.OK);
    }

	public static void main(String[] args) {
		SpringApplication.run(ApplicationExample.class, args);
	}

}
