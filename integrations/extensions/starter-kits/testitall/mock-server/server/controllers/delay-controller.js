export async function delay(req, res) {
  let sleep_duration = req.body.sleep_time || 3;

  await sleep(sleep_duration * 1000);

  return res.json({
    status: 'UP',
    sleep_duration
  });
}

function sleep(milliseconds) {
  return new Promise((resolve) => {
    setTimeout(resolve, milliseconds);
  });
}