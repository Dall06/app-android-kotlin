import db from "../config/db.ts";

const database = db.getDatabase;
const users = database.collection("users");

const generateBodyParams = async (context: any) => {
  const body = await context.request.body();
  const params = await body.value; // user from request

  return params;
};

const convertListObjectToKotlinReadable = (list: any) => {
  for (let index = 0; index < list.length; index++) {
    const convertedId = list[index]._id.$oid

    delete list[index]._id.$oid;
    list[index]._id = convertedId;
  }

  return list;
}

const convertObjectToKotlinReadable = (obj: any) => {
  const convertedId = obj._id.$oid

  delete obj._id.$oid;
  obj._id = convertedId;

  return obj;
}

const getUsers = async (context: any) => {
  var usersFromMongoDb = await users.find({});

  usersFromMongoDb = 
      convertListObjectToKotlinReadable(usersFromMongoDb);
  
  console.log(usersFromMongoDb)

  context.response.body = usersFromMongoDb;
};

const createUser = async (context: any) => {
  const params = await generateBodyParams(context);
  delete params._id

  const insertedUser = await users.insertOne(params);

  if (insertedUser) {
    context.response.body = { status: 'success', 'message': 'added in mongoDB' };
  } else {
    context.response.body = { status: 'error', 'message': 'cannot be added' };
  }
};

const getUserById = async (context: any) => {
  const userid = context.params.userid;
  let obtainedUser = await users.findOne({ _id: { "$oid": userid} });
  obtainedUser = convertObjectToKotlinReadable(obtainedUser)

  context.response.body = obtainedUser;
};

const getUsersByType = async (context: any) => {
  const userType = Number(context.params.userType);

  let obtainedUsers = await users.find({ userType: userType});
  obtainedUsers = convertListObjectToKotlinReadable(obtainedUsers);

  context.response.body = obtainedUsers;
};

const updateUserById = async (context: any) => {
  console.log("Update");
  const id = context.params.userid;

  const params = await generateBodyParams(context);
  
  delete params._id
  delete params.password
  
  const { matchedCount, modifiedCount } = await users.updateOne({ _id: { "$oid": id } }, { $set: params });

  if (matchedCount == 1 && modifiedCount == 1) {
    context.response.body = { status: 'success', 'message': 'updated' };
  } else {
    context.response.body = { status: 'failed', 'message': 'failed to update' };
  }
};

const deleteUser = async (context: any) => {
  // const userid = context.params.userid;

  const params = await generateBodyParams(context);

  const userToDelete: any = await users.findOne({ 
    _id: { 
      "$oid": params._id 
    }
  });

  if (userToDelete.password === params.password) {
    await users.deleteOne({ _id: { "$oid": params._id } });
    context.response.body = { status: 'success', 'message': 'deleted' };
  } else {
    context.response.body = { status: 'failed', 'message': 'failed to delete' };
  }
}

const loginUser = async (context: any) => {
  const params = await generateBodyParams(context);

  let userToLog: any = await users.findOne({ email: params.email });
  userToLog = convertObjectToKotlinReadable(userToLog)

  if (userToLog.password === params.password) {
    context.response.body = { status: 'success', message: 'logged', body: userToLog };
  } else {
    context.response.body = { status: 'failed', message: 'error' };
  }
};

export { getUsers, createUser, getUserById, getUsersByType, updateUserById, deleteUser, loginUser };