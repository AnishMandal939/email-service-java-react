import toast from "react-hot-toast";

export const handleCustomToast = (status, message) => {
  switch (status) {
    case "success":
      toast.success(message, {
        position: "bottom-right",
        autoClose: 700,
        closeButton: false,
        icon: "🚀",
      });
      break;
    case "error":
      toast.error(message, {
        position: "bottom-right",
        autoClose: 700,
        closeButton: false,
        icon: "❌",
      });
      break;
    case "pending":
      toast(message, {
        position: "bottom-right",
        autoClose: 700,
        closeButton: false,
        icon: "🕒",
      });
      break;
    case "info":
      toast(message, {
        position: "bottom-right",
        autoClose: 700,
        closeButton: false,
        icon: "🔔",
      });
      break;
    case "warning":
      toast(message, {
        position: "bottom-right",
        autoClose: 700,
        closeButton: false,
        icon: "⚠️",
      });
      break;
    case "custom":
      toast(message, {
        position: "bottom-right",
        autoClose: 700,
        closeButton: false,
        icon: "🎉",
      });
      break;
    default:
      break;
  }
};
