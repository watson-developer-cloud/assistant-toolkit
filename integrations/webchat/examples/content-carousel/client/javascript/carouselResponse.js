import Swiper, { A11y, Navigation, Pagination } from 'swiper';

/**
 * This function is called whenever web chat receives a custom response and we need construct the content for web chat
 * to display.
 */
function carouselCustomResponseHandler(event, instance) {
  const { element, message } = event.data;

  // This will define the main HTML content we want to display in this custom response. We will add the individual
  // carousel items below.
  element.innerHTML = `
    <div class="Carousel">
      <div class="swiper">
        <div class="swiper-wrapper"></div>
      </div>
      <div class="Carousel__Navigation" >
        <button type="button" class="Carousel__NavigationButton Carousel__NavigationPrevious bx--btn bx--btn--ghost">
          <svg fill="currentColor" width="16" height="16" viewBox="0 0 32 32" aria-hidden="true"><path d="M20 24L10 16 20 8z"></path></svg>
        </button>
        <div class="Carousel__BulletContainer"></div>
        <button type="button" class="Carousel__NavigationButton Carousel__NavigationNext bx--btn bx--btn--ghost">
          <svg fill="currentColor" width="16" height="16" viewBox="0 0 32 32" aria-hidden="true"><path d="M12 8L22 16 12 24z"></path></svg>
        </button>
      </div>
    </div>`;

  // Once we have the main HTML, create each of the individual slides that will appear in the carousel.
  const slidesContainer = element.querySelector('.swiper-wrapper');
  createSlides(slidesContainer, message, instance);

  // Initialize the Swiper library which is what we are using to control the carousel. We are using a custom pagination
  // element to control pagination and navigation.
  // eslint-disable-next-line no-new
  new Swiper(element.querySelector('.swiper'), {
    modules: [Navigation, Pagination, A11y],
    keyboard: {
      enabled: true,
    },
    pagination: {
      el: element.querySelector('.Carousel__BulletContainer'),
      clickable: true,
      bulletClass: 'Carousel__Bullet',
      bulletActiveClass: 'Carousel__Bullet--selected',
      renderBullet,
    },
    navigation: {
      prevEl: element.querySelector('.Carousel__NavigationPrevious'),
      nextEl: element.querySelector('.Carousel__NavigationNext'),
    },
    slidesPerView: 'auto',
    spaceBetween: 15,
    centeredSlides: true,
    rewind: true,
  });
}

/**
 * This function will create the individual slides to display in the carousel. This will use the "carousel_data" value
 * that is provided as part of the message from the assistant. Each slide will have two buttons. The first button
 * will be a link to another web page. The second button will send a message to the assistant to get more data
 * within web chat for the desired card.
 */
function createSlides(slidesContainer, message, webChatInstance) {
  const carouselData = message.user_defined.carousel_data;

  // Create a slide for each credit card in the message custom data.
  carouselData.forEach((cardData) => {
    const { url, title, description, alt } = cardData;
    const cardElement = document.createElement('div');
    cardElement.classList.add('swiper-slide');

    cardElement.innerHTML = `
      <div class="bx--tile Carousel__Card">
        <img class="Carousel__CardImage" src="${url}" alt="${alt}" />
        <div class="Carousel__CardText">
          <div class="Carousel__CardTitle">${title}</div>
          <div class="Carousel__CardDescription">${description}</div>
        </div>
        <!-- Here you would use a link to your own page that shows more details about this card. -->
        <a href="https://www.ibm.com" class="Carousel__CardButton bx--btn bx--btn--primary" target="_blank">
          View more details
        </a>
        <!-- This button will send a message to the assisstant and web chat will respond with more info. -->
        <button type="button" class="Carousel__CardButton Carousel__CardButtonMessage bx--btn bx--btn--primary">
          Tell me more about this
        </button>
      </div>
    `;

    // Add a click handler to the second link/button. This will send a silent message to the assistant to ask for
    // more information about the given credit card.
    const button = cardElement.querySelector('.Carousel__CardButtonMessage');
    button.addEventListener('click', () => {
      webChatInstance.send({ input: { text: `Tell me about ${title}` } }, { silent: true });
    });

    slidesContainer.appendChild(cardElement);
  });
}

/**
 * Renders a custom bullet to be displayed in the pagination element.
 */
function renderBullet(_, className) {
  return `<button class="${className} bx--btn bx--btn--ghost" type="button"></button>`;
}

export { carouselCustomResponseHandler };
