import os
import threading

from dotenv import load_dotenv

from utils import split_into_paragraphs, count_tokens, extract_last_paragraph

from ibm_watson_machine_learning.foundation_models import Model
from ibm_watson_machine_learning.metanames import GenTextParamsMetaNames as GenParams
from ibm_watson_machine_learning.foundation_models.utils.enums import ModelTypes


load_dotenv()  # take environment variables from .env

# The model used for summarization
MODEL_ID = ModelTypes.LLAMA_2_70B_CHAT
# The maximum number of tokens that can be passed to the model. If the input text is longer than this, it will be split
# into multiple paragraphs and each paragraph will be summarized separately.
MAX_INPUT_TOKENS = 2048
# The minimum and maximum number of tokens that will be generated by the model.
MIN_NEW_TOKENS = 50
MAX_NEW_TOKENS = 500
# The prompt that will be prepended to the input text before summarization.
SUMMARIZATION_PROMPT = ("Please summarize the following text into a single paragraph with 3-4 sentences. "
                        "Output complete sentences. "
                        "Use professional language. "
                        "Output only the summarized result, do not repeat the prompt. "
                        "Here is the text:")

# To display example params enter
GenParams().get_example_values()

generate_params = {
    GenParams.MIN_NEW_TOKENS: MIN_NEW_TOKENS,
    GenParams.MAX_NEW_TOKENS: MAX_NEW_TOKENS,
}

model = Model(
    model_id=MODEL_ID,
    params=generate_params,
    credentials={
        "apikey": os.getenv('WATSONX_API_KEY'),
        "url": os.getenv('WATSONX_API_URL')
    },
    project_id=os.getenv('WATSONX_PROJECT_ID')
)


def generate(prompt: str) -> str:
    generated_response = model.generate(prompt=prompt)
    return generated_response['results'][0]['generated_text']


def _summarize(text: str) -> str:
    return extract_last_paragraph(generate(SUMMARIZATION_PROMPT + "\n\n" + text))


def summarize(text: str, max_input_tokens: int = MAX_INPUT_TOKENS) -> str:
    print(f'Summarizing {count_tokens(text)} tokens')

    paragraphs = split_into_paragraphs(text, max_input_tokens)

    if len(paragraphs) == 1:
        print('Only one paragraph, summarizing it directly')
        summary = _summarize(paragraphs[0])
        print(f'> {summary}')
        return summary

    print(f'Split into {len(paragraphs)} paragraphs')
    paragraph_summaries = [""] * len(paragraphs)
    threads = []

    for i, paragraph in enumerate(paragraphs):
        def target(i, paragraph):
            print(f'Started summarization thread for paragraph {i}')
            paragraph_summaries[i] = _summarize(paragraph)
            print(f'Finished summarization thread for paragraph {i}')
            print(f'> {paragraph_summaries[i]}')

        t = threading.Thread(target=target, args=(i, paragraph))
        threads.append(t)
        t.start()

    for t in threads:
        t.join()

    paragraph_summary = '\n\n'.join(paragraph_summaries)

    print('Combining paragraph summaries')
    summary = summarize(paragraph_summary)
    print(f'> {summary}')

    if len(summary) > len(paragraph_summary):
        print('Summary is longer than the paragraph_summary text, returning paragraph_summary')
        return paragraph_summary

    return summary