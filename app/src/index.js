import ReactDOM from 'react-dom';
// import App from './App';

// ReactDOM.render(<App />, document.getElementById('root'));


// create classes
var NavBar = React.createClass({
    render: function(){
        return(
            <nav className="navbar navbar-inverse">
                <div className="container-fluid nav-bar-control">
                    <div className="navbar-header col-md-3">
                        <button type="button" className="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse" aria-expanded="false">
                            <span className="sr-only">Toggle navigation</span>
                            <span className="icon-bar"></span>
                            <span className="icon-bar"></span>
                            <span className="icon-bar"></span>
                        </button>
                        <NavBrand linkTo={this.props.brand.linkTo} text={this.props.brand.text} />
                    </div>
                    <div className="col-md-6">
                        <form className="navbar-form" role="search">
                            <div className="form-group col-md-10">
                                <input type="text" className="form-control main-search-form-control" placeholder="Search" />
                            </div>
                            <div className="col-md-2">
                                <button type="submit" className="btn btn-default">Ask!</button>
                            </div>
                        </form>
                    </div>
                    <div className="collapse navbar-collapse col-md-3" id="navbar-collapse">
                        <NavMenu links={this.props.links} />
                    </div>
                </div>
            </nav>
        );
    }
});

var NavBrand = React.createClass({
    render: function(){
        return (
            <a className="navbar-brand" href={this.props.linkTo}>{this.props.text}</a>
        );
    }
});

var NavMenu = React.createClass({
    render: function(){
        var links = this.props.links.map(function(link){
            if(link.cart) {
                return (
                    <NavCart />
                );
                // return (
                //     <NavLinkDropdown links={link.links} text={link.text} active={link.active} />
                // );
            }
            else {
                return (
                    <NavLink linkTo={link.linkTo} text={link.text} active={link.active} />
                );
            }
        });
        return (
            <ul className="nav navbar-nav">
                {links}
            </ul>
        );
    }
});

class NavLink extends React.Component {
    render() {
        return(
            <li className={(this.props.active ? "active" : "")}>
                <a href={this.props.linkTo}>{this.props.text}</a>
            </li>
        );
    }
}

class NavCart extends React.Component {
    render() {
        return <li className={(this.props.className === undefined ? "" : this.props.className)}>
            <a href={this.props.linkTo}>
                <span className="fa fa fa-shopping-cart fa-2x" aria-hidden="true"></span>
            </a>
        </li>;
    }
}

// set data
let navbar = {};
navbar.brand = {linkTo: "#", text: "Askfolio"};
navbar.links = [
    {linkTo: "#", text: "Log In"},
    {linkTo: "#", text: "Become a Lender"},
    {linkTo: "#", cart: true, className: "nav-bar-cart"}
];

// render NavBar
ReactDOM.render(
    <NavBar {...navbar} />,
    document.getElementById("navbar")
);

const TopImage = (
    <div className="index-top-image"></div>
);

// ReactDOM.render(
//     topImage,
//     document.getElementById('root')
// );

const MidImage = (
    <div className="row">
        <div className="col-md-4 index-top-image"></div>
        <div className="col-md-4 index-top-image"></div>
        <div className="col-md-4 index-top-image"></div>
    </div>
);

ReactDOM.render(
    TopImage,
    document.getElementById('top')
);

ReactDOM.render(
    MidImage,
    document.getElementById('mid')
);

