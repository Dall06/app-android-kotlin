import { Application } from "https://deno.land/x/oak@v6.3.2/mod.ts";

import router from "./routes.ts";

const app = new Application();

app.use(router.routes());
app.use(router.allowedMethods());

const startServer = async () => {
  const port: number = 8000;
  
  console.log('Server running on port', port);
  await app.listen({ port: port });
};

startServer();
