// delay for a few seconds

export function delay(req, res, next) {
  let sleep_duration =30000;
  sleep_duration = req.body.sleep_time
  // console.log(`Delay ${sleep_duration}`);
  sleep(sleep_duration*1000);

  res.json({
    status: 'UP',
    sleep_duration
  });
};

function sleep(milliseconds) {
  const date = Date.now();
  let currentDate = null;
  do {
    currentDate = Date.now();
  } while (currentDate - date < milliseconds);
};
