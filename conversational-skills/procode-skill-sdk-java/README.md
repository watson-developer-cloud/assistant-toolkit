# Conversational Skills SDK for watsonx Assistant (Maven Artifact)

## Installation
This SDK allows implementing a Java-based conversational skill for watsonx Assistant.

To utilize, simply add this artifact as a dependency in your maven project.
```
<dependency>
    <groupId>com.ibm.watson</groupId>
    <artifactId>conversationalskills-sdk</artifactId>
    <version>0.0.2</version>
</dependency>
```

## Usage
A conversational skill must be implemented as a subclass of the com.ibm.watson.conversationalskills.sdk.Skill class:

```java
@RequestScoped
public class SkillImplementation extends Skill {
	…
}
```

In addition, each slot must be implemented as a subclass of com.ibm.conversationalskills.sdk.SlotHandler:

```java
@RequestScoped
public class SlotHandlerImplementation extends SlotHandler {
	…
}
```

The following REST endpoints MUST be exposed by the provider server:

/providers/{provider_id}/conversational_skills (GET)

/providers/{provider_id}/conversational_skills/{conversational_skill_id} (GET)

/providers/{provider_id}/conversational_skills/{conversational_skill_id}/orchestrate (POST)

The documentation of these REST endpoints can be found [here](https://github.com/watson-developer-cloud/assistant-toolkit/blob/master/conversational-skills/procode-endpoints.md).

## Example
An example of using the sdk to create a springboot rest application exposing necessary endpoints can be found in the [springboot example folder](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/conversational-skills/procode-skill-springboot-example).
