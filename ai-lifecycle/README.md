# AI Lifecycle

## Introduction
We want to help you build unique conversational experiences which work seamlessly with your end users. We want to help get your projects off the ground quickly, and build virtual conversational assistants which impact the KPIs you care about most.

We believe that if we can help you leverage all of the capabilities available within IBM watsonx Assistant, you will be able to build highly effective AI solutions for your business. To that end, experts from the IBM watsonx Assistant team have compiled a set of guidelines that are covered in this lifecycle document. The guidelines incorporate expertise that the team has gained from real world engagements with clients from around the world. We hope it helps you to discover ways to get the most out of your own AI implementation.
 
## Phases of the AI lifecycle
The AI lifecycle has six phases. The phases are repeated to form an iterative process that you follow to build, manage, and incrementally improve your assistant

The six phases are illustrated in the figure below
![Phases of the AI Lifecycle](https://github.com/watson-developer-cloud/assistant-toolkit/blob/master/ai-lifecycle/AILifecycle.png "Phases of the AI Lifecycle")

## Phase 1: Create and update training data


In this phase of the lifecycle, you create an assistant or update an existing assistant. If this is the first time you are to improve it you should check out the getting started guide to familiarize yourself with the [product documentation](https://cloud.ibm.com/docs/services/assistant). 

By following the process mentioned in the [development process guide](https://cloud.ibm.com/docs/services/assistant?topic=assistant-dev-process), you can convert the issues for which end users reach out to a contact center, into a business use case in which an assistant works in sync with a human agent. Using the "Try It Out" panel, you can then test your assistant. Submit utterances that represent reasons for contacting support and see whether you assistant returns the correct intent. 

In this phase, it's normal for some utterances to be mapped to the correct intents while others may incorrectly map to a different intent. The next phase of the lifecycle introduces additional tools that can help you improve on the initial design of your virtual assistant. 


## Phase 2: Analyze train and test data


In this phase of the lifecycle, you analyze your assistant by using the [Dialog Skill Analysis Notebook](https://github.com/watson-developer-cloud/assistant-dialog-skill-analysis). You then use the insights generated from the data to make updates. You can use the notebook to analyze characteristics of your data, such as the number of training examples for each intent or the terms which seem to be characteristic of a specific intent. We have found that reviewing this analysis can help you uncover potential pitfalls in the design of your dialog skill.


In order to perform quantitative analysis you need to create a test set that can help analyze the performance of your existing dialog skill. A test set in this phase is just additional data which contains examples of utterances for different intents. This test set is also known as a blind test set. As the name implies this data should not be the same as the examples provided during creation of the dialog skill. These could be additional examples drawn from logs or could be a set of additional examples created by subject matter experts. Ideally, for every 10 examples provided as training data for an intent, you will provide an additional 2 or 3 examples for testing that intent. 


Use the test set with the notebook to generate quantitative metrics about how the dialog skill's machine learning models perform. Remember that it is important that examples provided in the test set are indicative of traffic in the actual use case. 


## Phase 3: Deploy


In this phase of the lifecycle, you have gone through the process of creating, analyzing and then updating your virtual assistant multiple times and are now interested in creating deployment processes for your virtual assistant. 


In this phase, we recommend that you go through the documentation related to deploying a virtual assistant. Reviewing the section on how to call out to your virtual assistants from within your use cases using the IBM watsonx Assistant APIs can help with the integration of the dialog skill into the final business process. Documentation related to [Software Development Kits (SDK)](https://cloud.ibm.com/docs/services/assistant?topic=watson-using-sdks) currently supported in various programming languages can also be found there


## Phase 4: Measure live system


After your virtual assistant is running in production and has active users, you can leverage the data produced by user interactions to find opportunities for further improvement. The [Continuous Improvement Best Practices Guide](https://github.com/watson-developer-cloud/assistant-improve-recommendations-notebook/raw/master/notebook/IBM%20Watson%20Assistant%20Continuous%20Improvement%20Best%20Practices.pdf) introduces the concept of measuring both the Effectiveness and the Coverage of your virtual assistant, and how you can use these metrics to prioritize your effort.  


The [Measure Notebook](https://github.com/watson-developer-cloud/assistant-improve-recommendations-notebook) provides a set of automated metrics that help you monitor and understand the behavior of your live system using your conversation logs as input. The goal is to understand where your assistant is doing well vs where it isn’t, and to focus your improvement effort to one of the problem areas identified. For example, you might identify the most common areas of your dialog where utterances from a user are not covered, or which intents lead most often to abandoned conversations. 


As an output of the Measure notebook, you can also generate a subset of problematic conversations to be further assessed and analyzed by your team in Phase 5. 


## Phase 5: Analyze effectiveness and coverage

With measurements of your live system in hand, you can focus on understanding the patterns that lead to low performance. If you decide to improve the Coverage measurement, you’ll want to use the [Intent Recommendations](https://cloud.ibm.com/docs/services/assistant?topic=assistant-intent-recommendations) feature in IBM watsonx Assistant, which uses your logs to help you create new intents or expand existing intents with more training examples. 

If you turn your attention to improve the Effectiveness measurement, you can choose to either analyze the quality of intent and entity detection using the [Analyze Effectiveness Notebook](https://github.com/watson-developer-cloud/assistant-improve-recommendations-notebook) or focus instead on the quality of your dialog flows using the [Dialog Flow Analysis Notebook](https://github.com/watson-developer-cloud/assistant-dialog-flow-analysis). 

This analysis phase helps you prioritize the next steps of your improvement effort.


## Phase 6: Improve via recommendations


In this phase of the lifecycle, you make further improvements to your virtual assistant based on the log analysis provided by the Assistant Effectiveness Notebook or the Dialog Flow Analysis Notebook, or the insights provided by the Dialog Skill Analysis Notebook. These improvements can include resolving intent conflicts seen in your data, adding training to imprecise intents, or combining confused intents into a single intent and distinguishing using entities.  As you update your training, you'll start Phase 1 of the next iteration of your AI lifecycle.


## In Conclusion
We hope that the guidance provided here helps you on your conversational AI journey with IBM watsonx Assistant. We also welcome feedback based on your experience designing, deploying and maintaining an assistant that supports your business process.
