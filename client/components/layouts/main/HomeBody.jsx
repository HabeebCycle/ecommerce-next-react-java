import { connect } from "react-redux";
import { setUserLogged } from "../../../management/reducers/authReducer/actions";

const HomeBody = (props) => {
  const { auth } = props;

  const handleLogin = () => {
    props.dispatch(setUserLogged(true));
  };

  const handleLogout = () => {
    props.dispatch(setUserLogged(false));
  };

  return (
    <>
      <p>Almost before we knew it, we had left the building.</p>
      <p>{auth.isLoggedIn ? "I'm Logged In" : "I'm logged out"}</p>
      {auth.isLoggedIn ? (
        <button onClick={handleLogout}>Log out</button>
      ) : (
        <button onClick={handleLogin}>Log In</button>
      )}
    </>
  );
};

const state = (appState) => ({
  auth: appState.auth,
});

//const actions = { setUserLogged };

export default connect(state)(HomeBody);
