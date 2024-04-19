import { useState } from "react";
import { sendEmail } from "../services/email.service";
import { handleCustomToast } from "../shared/customToast/handleCustomToast";
import { CustomLoader } from "../shared/loader/CustomLoader";

export const EmailSender = () => {
  const [emailData, setEmailData] = useState({
    to: "",
    subject: "",
    message: "",
  });
  const [loading, setLoading] = useState(false);

  function handleFieldChange(e, name) {
    setEmailData({ ...emailData, [name]: e.target.value });
  }

  async function handleSubmit(event) {
    event.preventDefault();
    if (!emailData.to || !emailData.subject || !emailData.message) {
      // alert("Please fill all the fields");
      //   toast.error("Please fill all the fields");
      handleCustomToast("error", "Please fill all the fields");
      return;
    }
    // send email using api - axios

    try {
      setLoading(true);
      await sendEmail(emailData);

      setEmailData({
        to: "",
        subject: "",
        message: "",
      });

      handleCustomToast(
        "success",
        `Email sent to ${emailData?.to} successfully!!`
      );
      handleCustomToast("info", "Send another one.");
    } catch (error) {
      console.log(error);
      handleCustomToast("error", `Email not sent`);
    }
    finally{
        setLoading(false);
    }
  }

  function handleClearField(e) {
    e.preventDefault();
    setEmailData({
      to: "",
      subject: "",
      message: "",
    });
  }
  return (
    <div className="w-full min-h-screen flex justify-center items-center">
      <div className="email_card md:w-1/3 w-full -mt-10 mx-4 md:mx-0 border shadow p-4 rounded-lg">
        <h1 className="text-3xl">Email Sender</h1>
        <p className="text-gray--700">
          Send email to your favorite person with your own app
        </p>
        <form action="" onSubmit={handleSubmit}>
          <div className="input__field mt-4">
            <div className="mb-5">
              <label
                htmlFor="email"
                className="block mb-2 text-sm font-medium  dark:text-white"
              >
                To whom you want to send email
              </label>
              <input
                value={emailData.to}
                onChange={(e) => handleFieldChange(e, "to")}
                type="email"
                id="email"
                className=" border border-gray-300 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="Enter your email here"
              />
            </div>
          </div>
          <div className="input__field mt-4">
            <div className="mb-5">
              <label
                htmlFor="Subject"
                className="block mb-2 text-sm font-medium  dark:text-white"
              >
                Subject
              </label>
              <input
                value={emailData?.subject}
                onChange={(e) => handleFieldChange(e, "subject")}
                type="text"
                id="Subject"
                className="bg-gray-50 border border-gray-300 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5  dark:border-gray-600 dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="Enter your subject here"
              />
            </div>
          </div>
          <div className="input__field mt-4">
            <div className="mb-5">
              <label
                htmlFor="message"
                className="block mb-2 text-sm font-medium dark:text-white"
              >
                Enter your message
              </label>
              <textarea
                value={emailData?.message}
                onChange={(e) => handleFieldChange(e, "message")}
                id="message"
                rows="8"
                className="block p-2.5 w-full text-sm bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="Write your thoughts here..."
              ></textarea>
            </div>
          </div>
          <div className="input__field mt-4">
            <div className="flex items-center justify-center w-full">
              <label
                htmlFor="dropzone-file"
                className="flex flex-col items-center justify-center w-full h-64 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer  dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-600 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600"
              >
                <div className="flex flex-col items-center justify-center pt-5 pb-6">
                  <svg
                    className="w-8 h-8 mb-4 text-gray-500 dark:text-gray-400"
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 20 16"
                  >
                    <path
                      stroke="currentColor"
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth="2"
                      d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2"
                    />
                  </svg>
                  <p className="mb-2 text-sm text-gray-500 dark:text-gray-400">
                    <span className="font-semibold">Click to upload</span> or
                    drag and drop
                  </p>
                  <p className="text-xs text-gray-500 dark:text-gray-400">
                    SVG, PNG, JPG or GIF (MAX. 800x400px)
                  </p>
                </div>
                <input id="dropzone-file" type="file" className="hidden" />
              </label>
            </div>
          </div>
          {/* buttons */}
          {loading && <CustomLoader message="Sending E-mail" />}
          <div className="button__container mt-4 flex justify-center gap-2">
            <button disabled={loading}
              type="submit"
              className="text-white bg-gradient-to-r from-green-400 via-green-500 to-green-600 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-green-300 dark:focus:ring-green-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2 disabled:text-gray-200 disabled:bg-gray-300 disabled:cursor-not-allowed disabled:opacity-50"
            >
              Send Email
            </button>
            <button disabled={loading}
              onClick={(e) => handleClearField(e)}
              className="text-white bg-gradient-to-r from-red-400 via-red-500 to-red-600 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-red-300 dark:focus:ring-red-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2 disabled:text-gray-200 disabled:bg-gray-300 disabled:cursor-not-allowed disabled:opacity-50"
            >
              Clear
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};
