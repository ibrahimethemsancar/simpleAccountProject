import logo from './logo.svg';
import './App.css';
import {Component} from "react";
import {Button} from "reactstrap";
import {Link} from "react-router-dom";

class Customer extends Component{
    state = {
        customer : {
            accounts: []
        }
    };
    async componentDidMount() {
        const response = await fetch('/v1/customer/273f7586-feac-4648-a7fa-ea7f393e3a30');
        const body = await response.json();
        this.setState({customer: body});
    }
    render() {
        const {customer} = this.state;
        return (
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo" />
                    <div className="App-intro">
                        <h2>Customer</h2>
                        {
                            <div key={customer.id}>
                                {customer.name} {customer.surname}

                                {
                                    customer.accounts.map(account =>
                                        (<p>Accounts : {account.id}, {account.balance}, {account.creationDate}

                                            {
                                                account.transactions.map(transaction =>
                                                    (<p>Transactions : {transaction.id}, {transaction.amount}, {transaction.transactionDate}</p>)
                                                )}
                                        </p>)
                                    )}
                            </div>
                        }
                        <Button color={"button"}><Link to={`/account/${customer.id}`}>Account</Link></Button>
                    </div>
                </header>
            </div>
        );
    }
}

export default Customer;
