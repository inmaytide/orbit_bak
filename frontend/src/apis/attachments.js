const API_ROOT = process.env.API_ROOT;

export default {
  upload: (belong) => `${API_ROOT}/attachments/${belong}`,
  download: (id) => `${API_ROOT}/attachments/${id}`
};
