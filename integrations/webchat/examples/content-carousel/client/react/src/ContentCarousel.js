import { CaretLeft16, CaretRight16 } from '@carbon/icons-react';
import PropTypes from 'prop-types';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation, Pagination, A11y } from 'swiper';
import { Tile, Button } from 'carbon-components-react';
import { useCallback, useState } from 'react';
import cx from 'classnames';

import everydayCard from './assets/lendyr-everyday-card.jpg';
import preferredCard from './assets/lendyr-preferred-card.jpg';
import topazCard from './assets/lendyr-topaz-card.jpg';

// Normally these images would be hosted on an external server but we are just going to hard-code them into this
// example to keep things simple. We're going to look at the hard-coded image data based on the URL that's returned
// in the message data.
const urlImageMap = new Map([
  ['lendyr-everyday-card.jpg', everydayCard],
  ['lendyr-preferred-card.jpg', preferredCard],
  ['lendyr-topaz-card.jpg', topazCard],
]);

/**
 * This is the component that renders our content carousel.
 */
function ContentCarousel({ message, webChatInstance }) {
  const carouselData = message.user_defined.carousel_data;

  const [navigationElement, setNavigationElement] = useState();

  const onCardClick = useCallback(
    (text) => {
      webChatInstance.send({ input: { text: `Tell me about ${text}` } }, { silent: true });
    },
    [webChatInstance],
  );

  // Create a slide for each credit card in the message custom data.
  return (
    <>
      {navigationElement && (
        <Swiper
          modules={[Navigation, Pagination, A11y]}
          keyboard={{
            enabled: true,
          }}
          pagination={{
            el: navigationElement.querySelector('.Carousel__BulletContainer'),
            clickable: true,
            bulletClass: 'Carousel__Bullet',
            bulletActiveClass: 'Carousel__Bullet--selected',
            renderBullet,
          }}
          navigation={{
            prevEl: navigationElement.querySelector('button:nth-of-type(1)'),
            nextEl: navigationElement.querySelector('button:nth-of-type(2)'),
          }}
          slidesPerView="auto"
          spaceBetween={15}
          centeredSlides
          rewind
        >
          {carouselData.map((cardData) => {
            const { url, title, description, alt } = cardData;
            const image = urlImageMap.get(url) || url;

            return (
              <SwiperSlide className="swiper-slide" key={url}>
                <Tile className="Carousel__Card">
                  <img className="Carousel__CardImage" src={image} alt={alt} />
                  <div className="Carousel__CardText">
                    <div className="Carousel__CardTitle">{title}</div>
                    <div className="Carousel__CardDescription">{description}</div>
                  </div>
                  {/* Here you would use a link to your own page that shows more details about this card. */}
                  <a
                    href="https://www.ibm.com"
                    className="Carousel__CardButton bx--btn bx--btn--primary"
                    target="_blank"
                    rel="noreferrer"
                  >
                    View more details
                  </a>
                  {/* This button will send a message to the assistant and web chat will respond with more info. */}
                  <Button
                    className="Carousel__CardButton Carousel__CardButtonMessage"
                    onClick={() => onCardClick(title)}
                  >
                    Tell me more about this
                  </Button>
                </Tile>
              </SwiperSlide>
            );
          })}
        </Swiper>
      )}
      <div
        ref={setNavigationElement}
        className={cx('Carousel__Navigation', { 'Carousel__Navigation--hidden': !navigationElement })}
      >
        <Button className="Carousel__NavigationButton" kind="ghost">
          <CaretLeft16 />
        </Button>
        <div className="Carousel__BulletContainer" />
        <Button className="Carousel__NavigatFionButton" kind="ghost">
          <CaretRight16 />
        </Button>
      </div>
    </>
  );
}

/**
 * Renders a custom bullet to be displayed in the pagination element.
 */
function renderBullet(_, className) {
  return `<span class="${className}"></span>`;
}

ContentCarousel.propTypes = {
  // eslint-disable-next-line react/forbid-prop-types
  message: PropTypes.object.isRequired,
  // eslint-disable-next-line react/forbid-prop-types
  webChatInstance: PropTypes.object.isRequired,
};

export { ContentCarousel };
