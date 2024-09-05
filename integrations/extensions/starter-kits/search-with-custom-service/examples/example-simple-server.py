import flask
import logging

SAMPLE_RESULT = {
    "url": "https://www.ibm.com/investor/articles/ibm-provides-update-to-management-system-and-reporting-segments",
    "body": "As we enter 2024, we continue to advance our hybrid cloud and AI strategy, with the completion of the Weather Company divestiture and updates to our management system. We are making changes to our reportable segments, to reflect the way the company manages operations and allocates resources. These actions better align our portfolio to our core strategy and to the underlying business models. These changes also provide more relevant information to investors. The following updates do not impact IBM’s consolidated results.\nThe Weather Company divested assets moving from Software segment to Other – divested businesses\nIn August 2023 we announced the divestiture of The Weather Company assets, and the transaction closed on January 31, 2024. In the first quarter of 2024, we realigned our management structure to have these assets managed outside the Software segment, within Other – divested businesses (Other). The movement of historical financials to Other is consistent with the treatment of other divested software and services content, and results in a Software segment more closely aligned to its go-forward business.\nSecurity Services moving from Software to Consulting\nTo better capture the growing security services market opportunity through our consulting engagements, management responsibility for Security Services has moved to Consulting. Security Services will have greater access to Consulting clients globally and will be able to capitalize on the scale of Consulting’s operating model and discipline. As a result, Security Services, previously reported within the Software segment will move to the Consulting segment.\nImproved transparency of segment profitability\nIBM’s reportable segments have historically included stock-based compensation and net interest expense allocations. As those decisions are made at the corporate level, these expenses have been removed from the business unit management system and will no longer be included in the reportable segments. This provides improved transparency of segment profitability and better comparability to industry peers.",
}

logger = logging.getLogger(__name__)
app = flask.Flask(__name__)

def run_search(query, metadata):
    # The logic to call out to your search system goes here.
    # In this example, we always return the sample text and URL with a title that is the incoming query
    return {"search_results":
            [
                {
                    "title": query,
                    "url": SAMPLE_RESULT['url'],
                    "body": SAMPLE_RESULT['body']
                }
            ]}


@app.route("/query", methods=["POST"])
def answer():
    logger.info(flask.request.json)
    data = flask.request.json
    query = data['query']
    metadata = data['metadata'] if 'metadata' in data else None
    return run_search(query, metadata)


if __name__ == '__main__':
    app.run()