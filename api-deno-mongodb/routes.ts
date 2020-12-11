import { Router } from "https://deno.land/x/oak@v6.3.2/mod.ts";
import { 
  getUsers, 
  createUser, 
  getUserById, 
  getUsersByType, 
  updateUserById, 
  deleteUser, 
  loginUser,
} from "./controllers/users.ts";
import { 
  getAllMedia, 
  addMedia, 
  getMediaById, 
  getMediaByArtistName, 
  updateMediaById, 
  deleteMedia 
} from "./controllers/media.ts";

const router = new Router();

router
  .get("/users", getUsers)
  .get("/users/:userid", getUserById)
  .get("/usersbytype/:userType", getUsersByType)
  .post("/users", createUser)
  .put("/user_update/:userid", updateUserById)
  .delete("/users", deleteUser)
  .post("/users/login", loginUser) // User fucntions ends in here
  .get("/media", getAllMedia)
  .get("/media/:mediaid", getMediaById)
  .get("/media/:artist", getMediaByArtistName)
  .post("/media", addMedia)
  .put("/media/:mediaid", updateMediaById)
  .delete("/media/:mediaid", deleteMedia); // Media functions ends in here

export default router;