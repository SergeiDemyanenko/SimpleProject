import React, {useState} from "react";
import axios from "axios";
import {Button} from "@material-ui/core";
import Paper from "@material-ui/core/Paper";
import TextField from "@material-ui/core/TextField";
import Box from "@material-ui/core/Box";
import Alert from "@material-ui/lab/Alert";

const LoginPage = ({history})=> {
    const [login, setLogin] = useState("")
    const [password, setPassword] = useState("")
    const [error, setError] = useState()

    const onLoginChange = e =>{
        setLogin(e.target.value)
    }

    const onPasswordChange = e =>{
        setPassword(e.target.value)
    }

    const onSubmit = async () =>{
            const result = await axios.post(`/api/login`, {
                login, password
            })

        if(result.data.success){
            localStorage.setItem("token", result.data.token)
            history.push("/")
        }else{
            setError(result.data.message)
        }
    }

    return <Box height = "70vh" display = "flex" justifyContent = "center" alignItems = "center">
            <Paper elevation={3} style = {{display: "flex", flexDirection: "column", height: "400px", alignItems: "center", justifyContent: "center", width: "600px"}}>
                <h3>Login</h3>
                <TextField style = {{marginBottom: "10px", width : "300px"}} id="outlined-basic" variant="outlined" label="Login" type="text"  value={login} onChange={onLoginChange}/>
                <TextField style = {{width : "300px"}} id="outlined-basic" variant="outlined" label="Password" type="password" value={password} onChange={onPasswordChange}/>
                <Button style = {{width : "300px", marginTop : "24px"}} id="outlined-basic" variant="contained" color="primary" onClick={onSubmit}>Login</Button>
                {error && <Alert style={{marginTop: "10px"}} severity="error">{error}</Alert>}
            </Paper>
        </Box>
}

export default LoginPage;
