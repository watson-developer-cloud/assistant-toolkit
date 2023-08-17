export function delay(req, res) {
  let sleep_duration = req.body.sleep_time || 3;

  sleep(sleep_duration * 1000);

  return res.json({
    status: 'UP',
    sleep_duration
  });
}

function sleep(milliseconds) {
  const date = Date.now();
  let currentDate = null;
  do {
    currentDate = Date.now();
  } while (currentDate - date < milliseconds);
}
