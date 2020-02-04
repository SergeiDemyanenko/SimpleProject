import React from 'react';

import TodoListItem from '../todo-list-item/todo-list-item';

import './todo-list.css';
import ItemEditForm from "../item-edit-form";

const TodoList = ({ items, onDelete, onEdit, onItemEdited }) => {

  const elements = items.map((item) => {
    const { id, ...itemProps } = item;
    if(item.toEdit){
      return (
          <li key={id} className="list-group-item">
            <ItemEditForm
                {...item}
                onItemEdited={onItemEdited}
            />
          </li>
      )
    }else{
      return (
          <li key={id} className="list-group-item">
            <TodoListItem
                { ...itemProps }
                onDelete={ () => onDelete(id) }
                onEdit={ () => onEdit(id)}/>
          </li>
      );
    }
  });
  return (<ul className="todo-list list-group">{ elements }</ul>);
};

export default TodoList;
