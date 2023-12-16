import {Component} from "react";

class Account extends Component {
    state = {
        account: {
            transactins: []
        }
    };

    async componentDidMount() {
        console.log(this.props.match.params.id);
       /* if(this.props.match.params.id !== 'new'){
         const client = await (await fetch(`/v1/accounts/${this.props.match.params.id}`)).json();
         this.setState({item:client});
        }*/
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;

        await fetch('/clients', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/v1/account');
    }
    render(){
        return <p>ds</p>
    }
}
export default Account;