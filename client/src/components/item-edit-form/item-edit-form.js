import React, { Component } from 'react';

import './item-edit-form.css';

export default class ItemEditForm extends Component {

  state = {
    label: ''
  };

  componentDidMount() {
    this.setState({
      label: this.props.label
    })
  }

  onLabelChange = (e) => {
    this.setState({
      label: e.target.value
    })
  };

  onEditClick = (e) => {
    e.preventDefault();
    const { label } = this.state;
    const cb = this.props.onItemEdited || (() => {});
    cb(this.props.id, label);
  };

  render() {
    return (
      <form
        className="center-panel d-flex"
        onSubmit={this.onEditClick}>

        <input type="text"
               className="form-control edit-todo-label"
               value={this.state.label}
               onChange={this.onLabelChange}
        />

        <button type="submit"
                className="btn btn-secondary">Edit</button>
      </form>
    );
  }
}
