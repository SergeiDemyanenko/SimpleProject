import React, { Component } from 'react';
import axios from 'axios';

import AppHeader from '../app-header';
import TodoList from '../todo-list';
import ItemAddForm from '../item-add-form';

import './app.css';


export default class App extends Component {

  state = {
    items: []
  };

  componentDidMount() {
    axios.get(`/api/list`)
        .then(res =>{
          const items = res.data.map((elem, index) => ({label: elem, id: index}));
          this.setState({items});
        }).catch();
  }

  onItemAdded = (label) => {
    axios.get(`/api/add?text=${label}`);
    this.setState((state) => {
      const item = this.createItem(label);
      return { items: [...state.items, item] };
    });
  };

  onItemEdited = ( id, label) => {
    axios.get(`/api/edit?text=${label}&id=${id}`);
    this.setState((state) => {
      const items = state.items.map((item) => {
        if(item.id === id){
          item.label = label;
          item.toEdit = false;
        }
        return item;
      });
      return { items };
    })
  };

  onDelete = (id) => {
    axios.get(`/api/delete?id=${id}`);
    this.setState((state) => {
      const idx = state.items.findIndex((item) => item.id === id);
      const items = [
        ...state.items.slice(0, idx),
        ...state.items.slice(idx + 1)
      ];
      items.forEach((elem, index) => {elem.id = index});
      return { items };
    });
  };

  onEdit = (id) => {
    this.setState((state) => {
      const items = state.items.map((item) => {
        item.id === id ? item.toEdit = true : item.toEdit = false;
        return item;
      });
      return  { items };
    });
  };

  createItem(label) {
    return {
      id: this.state.items.length,
      label,
      important: false,
      done: false
    };
  }

  render() {
    const { items } = this.state;

    return (
      <div className="todo-app">
        <AppHeader />

        <TodoList
          items={ items }
          onDelete={this.onDelete}
          onEdit={this.onEdit}
          onItemEdited={this.onItemEdited}/>

        <ItemAddForm
          onItemAdded={this.onItemAdded} />
      </div>
    );
  };
}