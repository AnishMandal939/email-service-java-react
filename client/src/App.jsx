import "./App.css";
import { EmailSender } from "./components/EmailSender";
import { Toaster } from "react-hot-toast";
import { LightSwitch } from "./shared/LightSwitch";

function App() {
  return (
    <>
      <LightSwitch />
      <EmailSender />
      <Toaster />
    </>
  );
}

export default App;
