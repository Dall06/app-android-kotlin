import db from "../config/db.ts";

const database = db.getDatabase;
const media = database.collection("media");

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

const getAllMedia = async (context: any) => {
  var obtainedMedia = await media.find({});

  obtainedMedia = convertListObjectToKotlinReadable(obtainedMedia)

  context.response.body = obtainedMedia;
};

const addMedia = async (context: any) => {
  const params = await generateBodyParams(context);

  delete params._id

  const insertedMedia = await media.insertOne(params);

  if (insertedMedia) {
    context.response.body = { status: 'success', 'message': 'added in mongoDB' };
  } else {
    context.response.body = { status: 'error', 'message': 'cannot be added' };
  }
};

const getMediaById = async (context: any) => {
  const mediaid = context.params.mediaid;
  const obtainedMedia = await media.findOne({ _id: { "$oid": mediaid } });

  context.response.body = obtainedMedia;
};

const getMediaByArtistName = async (context: any) => {
  const artist = context.params.artist;

  const obtainedMedia = await media.find({ userType: artist });

  context.response.body = obtainedMedia;
};

const updateMediaById = async (context: any) => {
  const mediaid = context.params.mediaid;

  const params = await generateBodyParams(context);

  const { matchedCount, modifiedCount } = await media.updateOne({ _id: { "$oid": mediaid } }, { $set: params });

  if (matchedCount == 1 && modifiedCount == 1) {
    context.response.body = { status: 'success', 'message': 'updated' };
  } else {
    context.response.body = { status: 'failed', 'message': 'failed to update' };
  }
};

const deleteMedia = async (context: any) => {
  const mediaid = context.params.mediaid;

  const deleteCount = await media.deleteOne({ _id: { "$oid": mediaid } });
  
  if(deleteCount > 0) {
    context.response.body = { status: 'success', 'message': 'deleted' };
  } else {
    context.response.body = { status: 'failed', 'message': 'failed to delete' };
  }
}

export { getAllMedia, addMedia, getMediaById, getMediaByArtistName, updateMediaById, deleteMedia };