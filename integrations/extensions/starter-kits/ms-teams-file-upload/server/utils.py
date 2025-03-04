import random
from datetime import datetime
from urllib.parse import unquote

from typing import Optional

import requests


def determine_file_name(response: requests.Response) -> str:
    name = ''
    if 'Content-Disposition' in response.headers:
        name = extract_file_name(response)

    if not name:
        name = generate_file_name()

    return name


def generate_file_name() -> str:
    # generate file name with date, time and random string
    return f"file_{datetime.now().strftime('%Y%m%d_%H%M%S')}_{''.join(random.choices('0123456789abcdef', k=8))}"


def extract_file_name(response: requests.Response) -> Optional[str]:
    header = response.headers['Content-Disposition']
    filename = ''
    for item in header.split(';'):
        if '=' in item:
            k, v = item.split('=')
            if k.strip() == 'filename*':
                filename = v[7:]
                break
            elif k.strip() == 'filename':
                filename = v.strip('"')
                break

    return unquote(filename)


def tokenize(text: str) -> list[str]:
    return text.split()


def count_tokens(text: str) -> int:
    return len(tokenize(text))


def split_into_paragraphs(text: str, max_tokens: int = 2048) -> list[str]:
    paragraphs = []
    tokens = tokenize(text)

    while len(tokens) > max_tokens:
        paragraph = ' '.join(tokens[:max_tokens])
        paragraphs.append(paragraph)
        tokens = tokens[max_tokens:]

    paragraphs.append(' '.join(tokens))

    return paragraphs


def determine_option_index(option_text: str) -> int:
    # e.g. option_text = "1. Option ABC" -> return 0
    try:
        return int(option_text.split('.')[0]) - 1
    except ValueError:
        raise ValueError(f"Unable to determine option index from text: {option_text}")


def extract_last_paragraph(text: str) -> str:
    return text.split('\n')[-1]


def format_search_result_text(text: str) -> str:
    return text.replace('<em>', '<b>').replace('</em>', '</b>')
