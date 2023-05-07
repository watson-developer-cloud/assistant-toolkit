# Language Model Personalization starter kit

You can use this starter kit to try a simple example of how generative language model can be used to provide a personalized response to a customer.  It illustrates the power of doing so, but it also demonstrates some disturbing and ethically dubious with some serious potential consequences.  The purpose of this kit is _not_ to illustrate a best-practice that we would recommend that someone follow.  Quite the contrary: we are putting this out there because we believe that users are starting to do these sorts of things with Watson Assistant right now, and we want to illustrate the power _and_ the dangers and provide guidelines for using this power more responsibly.  Furthermore, we intend in the future to add more examples that illustrate more cautious, responsible applications of this kind of capability.  See the "Responsible Use" section at the end of this README file for more details on this subject.

## Prerequisites

This starter kit requires that you use the [new Watson Assistant](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-welcome-new-assistant).

Create a new, empty assistant that you can use to test this starter kit. For more information, see [Adding more assistants](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-assistant-add).

## Connect your assistant to a language model using a custom extension

You connect your assistant by using an OpenAPI specification to add a custom extension.  You can see an example of how to do this in the [OpenAI starter kit](../language-model-openai), which shows how to connect to OpenAI models like GPT 3.5 and 4.  However, you can also do the same thing with any other generative language model that has a REST API using the same approach.  For details on how to use an alternative language model, see below.

## Upload sample actions

The starter kit includes a JSON file with these sample actions:

| Action | Description |
| --- | --- |
| Invoke GPT Chat Completion API | Connects to OpenAI with the `gpt-3.5-turbo` model, which provides a level of quality that is nearly as good as `text-davinci-003` at a much lower cost. |
| Invoke GPT Completion API | Connects to OpenAI with the `text-davinci-003` model. For more information, see [OpenAI Pricing](https://openai.com/pricing). |
| Close bank account | Simple example of how OpenAI can be used to provide a personalized response to a customer. |

To use the sample actions:

1. Download the sample actions from this starter kit: [Personalization-Using-LLMs-action.json](./Personalization-Using-LLMs-action.json).

1. Use **Actions Global Settings** to upload the JSON file to your assistant. For more information, see [Uploading](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-admin-backup-restore#backup-restore-import).

## Preview the sample actions

To preview the sample actions:

1. On the **Actions** page, click **Preview**.

1. Enter `I want to close my account`. The assistant asks `I'm sorry to hear that you want to close your bank account. Why do you want to close your account?`.

1. You can try various answers and OpenAI generates a personalized answer. For example, try: `I recently got married and I'm planning on moving to my wife's bank. I want to close my account and transfer my remaining funds to our new account`.

1. The `Invoke GPT Chat Completion API` action is recognized. The assistant responds with a personalized response.

### Details about the Close bank account sample action

| Step | Description |
| --- | --- |
| 1 | Sets hardcoded values for three variables: `customer_name`, `account_type`, and `email`. You can modify these values as you want. In a real application, you would probably get them from a database if your user was logged in to the site where your assistant is deployed.  |
| 2 | Uses the variable `model_prompt` to ask OpenAI to pretend to be a bank and personalize the response based on the customer's reason from step 1. Set to use the `Invoke GPT Chat Completion API` action, which uses the `gpt-3.5-turbo` model. Depending on your OpenAI plan, you can modify to use the `Invoke GPT Completion API` action, which uses the more expensive `text-davinci-003` model. |
| 3 | Uses the variable `model_response` to show the response from OpenAI to the customer. |
| 4 | Example options step |
| 5 | Example connect to agent step |

The `Close bank account` action currently connects to OpenAI using the `Invoke GPT Chat Completion API` action, but you can change the action call out in step 2 of this action to call a different model-invoking action instead.  The model-invoking action needs to take in the `model_prompt` session variable, prompt the model using that prompt, and then set `model_responded` to true or false depending on whether the model provided a response and `model_response` to that response if it did.  The `Invoke GPT Completion API` action meets this requirement too, so you change the action call out in step 2 of `Close bank account` to call `Invoke GPT Completion API` action if you want to try calling the slightly higher-quality (but much more expensive) `text-davinci-003` model.  Furthermore, if you create an action that invokes some _other_ language model using `model_prompt` as input and `model_responded` and `model_response` as outputs, you can change the action call out to call that _other_ model-invoking action instead.

## Responsible Use

The full prompt expression in step 2 is:

> "Pretend you are a bank and that the customer wants to close their bank account because " + ${step_457} + ". address the customer directly in the chat window and convince them to stay with the bank? Personalize with any info they provide. If you don't know the customer's name then address the customer generically. Here are some details about the customer, if any of the values are null just ignore and don't mention them: customer name: ${customer_name}, and customer email ${email}, and account type: ${account_type}. Limit your response to 400 characters without trailing off, don't mention this to the customer."

What we see when we use this model with models like the GPT 3.5 and 4 models is that the results are often very impressive and persuasive but are also often erratic, unpredictable, and include entirely made-up facts.  For example, consider the following transcript:

> User: close bank account

> Watson Assistant: I'm sorry to hear that you want to close your bank account. Why do you want to close your account?

> User: I am moving to London

> Watson Assistant: Hello Jane, we're sorry to hear that you're planning to close your savings account as you move to London. Did you know that our bank has a wide network and presence in London? By keeping your savings account with us, you can continue to enjoy our competitive interest rates and access to our services. We'd love to keep you as a customer and help you with your banking needs. Let us know if there's anything we can do to make banking with us easier for you in London.

> Watson Assistant: I hope that helps. Do you still want to close your account? Or would you like to talk to a representative and discuss your options?

It is very impressive that the model (`gpt-3.5-turbo` in this example) is able to directly respond to the specific issue that the customer has and make claims that respond directly to the concerns the user has.  Note, however, that the model says things like "Did you know that our bank has a wide network and presence in London?", but we never sent the model any information that suggests that the (hypothetical) bank for this sample chatbot has any presence in London.  This is coming from the model for two reasons:

1. Our prompt told the model to convince the customer to stay with the bank, so it is trying to do so.  It has no facts about this bank, so it can only make up facts to support its argument.
2. Even if it did have some usable facts about the bank in its prompt, it would be free to make up more if needed to build an argument.

The nature of large language models like GPT 3.5 here is that they excel at generating _seemingly plausible_ content because they are trained on huge volumes of content and have a lot of powerful statistics about what content tends to appear in any given context.  In this case, it knows what a convincing argument for keeping a customer who is moving to London would look like so it generates one, without any real _understanding_ that this argument includes entirely made-up facts or that made-up facts can have real consequences.

A real-world customer who relies on this answer might keep their money in the same bank when moving to London based on the claims made by the chatbot, only to find they have no access to their money because the purported presence in London does not exist.  Such a customer could face severe financial hardship.  If a customer got this information from a chatbot on the bank's website, that customer could have a serious legal claim against the bank.

What can be done about this?  Here are some guidelines to consider:

1. Before using a language model to personalize an response, think about the severity of the consequences if the response includes false information.  If the consequences are very serious, do not use automatic personalization at all.
2. In some cases, adjusting the prompt can _reduce_ (but not completely eliminate) the propensity for misinformation.  For example, if the prompt said explicitly that the response should not making any specific claims or promises about the bank or the services it provides, then the risk of a claim like "our bank has a wide network and presence in London" would be _less_ likely to appear.  Of course, an argument for staying with a bank that makes no specific claims or promises is not likely to be very convincing.  One way to overcome that might be to add some specific facts into the prompt that the language model can use as needed to build an argument and tell the model it can use those facts but not make up any others.  This _can_ work, but the more complex the instructions you provide are, the more likely the model is to fail to comply with all of them.  So it can be a lot of work to find a prompt that provides useful results but also rarely produces misinformation.
3. In some cases, changing to a different language model can make a difference.  Different language models have different degrees to which they can be trusted to follow instructions and different degrees to which they are prone to produce unsubstantiated outputs.
4. You typically want to do a lot of testing with these.  Unless you have configured your models to always produce the same output every time it gets the same input (which we have not done in this sample kit), you may want to try _each_ of your test inputs several times to see how _often_ it produces misinformation.

Note that 2-4 are methods for reducing misinformation, but none of them can completely eliminate it.  If you have a very clear, heavily-constrained prompt, a very reliable model, and a large set of test cases that you run many times, you can never be sure that a real-world use will not result in some false information being produced.  So use guidelines 2-4 as a complement to guideline 1, not a replacement for it.
