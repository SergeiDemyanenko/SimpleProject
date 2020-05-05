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

  token = localStorage.getItem("token");

  componentDidMount() {
    axios.get(`/api/list-obj`, {
        headers: {'Authorization': this.token}
    })
        .then(response => {
          const items = response.data.map((elem) => ({label: elem.text, id: elem.id}));
          this.setState({items});
        }).catch(error => {
          alert('Sorry, the server is unavailable :(');
        });
  }

  onItemAdded = (label) => {
    axios.post(`/api/add`, {
        text: label
    })
        .then(response => {
          this.setState((state) => {
            const item = this.createItem(response.data.id, label) ;
            return { items: [...state.items, item] };
          });
        }).catch(error => {
          alert('Sorry, the server is unavailable :(');
        });
  };

  onItemEdited = ( id, label) => {
    axios.patch(`/api/edit`, {
        id: id,
        text: label
    })
        .then(response => {
          this.setState((state) => {
            const items = state.items.map((item) => {
              if (item.id === id) {
                item.label = label;
                item.toEdit = false;
              }
              return item;
            });
            return { items };
          });
        }).catch(error => {
            alert('Sorry, the server is unavailable :(');
        });
  };

  onDelete = (id) => {
    axios.delete(`/api/delete?id=${id}`)
        .then(response => {
          this.setState((state) => {
            const idx = state.items.findIndex((item) => item.id === id);
            const items = [
              ...state.items.slice(0, idx),
              ...state.items.slice(idx + 1)
            ];
          items.forEach((elem, index) => {elem.id = index});
          return { items };
          });
        }).catch(error => {
          alert('Sorry, the server is unavailable :(');
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

  createItem(id, label) {
    return {
      id,
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